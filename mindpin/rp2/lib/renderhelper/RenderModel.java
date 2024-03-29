package mindpin.rp2.lib.renderhelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;

import mindpin.MCGEEK;
import mindpin.rp2.lib.math3d.TexVertex;
import mindpin.rp2.lib.math3d.Vector3;

public class RenderModel {
	public Vector3[] vertexList;
	public TexVertex[][] texList;
	int[][][] groups;

	public static RenderModel loadModel(String name) {
		InputStream is = MCGEEK.class.getResourceAsStream(name);

		ModelReader ml = new ModelReader();
		try {
			ml.readModel(is);
		} catch (IOException e) {
			return null;
		}
		ArrayList<TexVertex[]> vtl = new ArrayList<TexVertex[]>();

		int i = 0;
		while (i < ml.faceno.size()) {
			TexVertex[] tv = new TexVertex[4];
			for (int j = 0; j < 4; j++) {
				int n = ml.faceno.get(i);
				i++;
				if (n < 0) {
					throw new IllegalArgumentException("Non-Quad Face");
				}

				int m = ml.faceno.get(i);
				i++;

				TexVertex t = ml.texvert.get(m - 1).copy();
				t.vtx = (n - 1);
				t.v = (1.0D - t.v);
				tv[j] = t;
			}
			int n = ml.faceno.get(i);
			i++;
			if (n >= 0) {
				throw new IllegalArgumentException("Non-Quad Face");
			}

			vtl.add(tv);
		}

		RenderModel tr = new RenderModel();
		tr.vertexList = ml.vertex.toArray(new Vector3[0]);
		tr.texList = vtl.toArray(new TexVertex[0][]);

		tr.groups = new int[ml.grcnt.size()][][];
		for (i = 0; i < ml.grcnt.size(); i++) {
			int l = ml.grcnt.get(i);
			tr.groups[i] = new int[l][];
			for (int j = 0; j < ml.grcnt.get(i); j++) {
				tr.groups[i][j] = new int[2];
			}
		}

		i = 0;
		int lgs = -1;
		int lgmn = -1;
		int lgsn = -1;
		while (i < ml.groups.size()) {
			if (lgs >= 0) {
				tr.groups[lgmn][lgsn][0] = lgs;
				tr.groups[lgmn][lgsn][1] = ml.groups.get(i + 2);
			}
			lgmn = ml.groups.get(i);
			lgsn = ml.groups.get(i + 1);
			lgs = ml.groups.get(i + 2);
			i += 3;
		}
		if (lgs >= 0) {
			tr.groups[lgmn][lgsn][0] = lgs;
			tr.groups[lgmn][lgsn][1] = ml.fno;
		}
		return tr;
	}

	public void scale(double sf) {
		for (int i = 0; i < this.vertexList.length; i++)
			this.vertexList[i].multiply(sf);
	}

	public static class ModelReader {
		public ArrayList<Vector3> vertex;
		public ArrayList<Integer> faceno;
		public ArrayList<TexVertex> texvert;
		public ArrayList<Integer> groups;
		public ArrayList<Integer> grcnt;
		int fno = 0;

		public ModelReader() {
			this.vertex = new ArrayList<Vector3>();
			this.faceno = new ArrayList<Integer>();
			this.texvert = new ArrayList<TexVertex>();
			this.groups = new ArrayList<Integer>();
			this.grcnt = new ArrayList<Integer>();
		}

		private void eatline(StreamTokenizer tok) throws IOException {
			while (tok.nextToken() != -1)
				if (tok.ttype == 10)
					;
		}

		private void endline(StreamTokenizer tok) throws IOException {
			if (tok.nextToken() != 10)
				throw new IllegalArgumentException("Parse error");
		}

		private double getfloat(StreamTokenizer tok) throws IOException {
			if (tok.nextToken() != -2) {
				throw new IllegalArgumentException("Parse error");
			}
			return tok.nval;
		}

		private int getint(StreamTokenizer tok) throws IOException {
			if (tok.nextToken() != -2) {
				throw new IllegalArgumentException("Parse error");
			}
			return (int) tok.nval;
		}

		private void parseface(StreamTokenizer tok) throws IOException {
			while (true) {
				tok.nextToken();
				if ((tok.ttype == -1) || (tok.ttype == 10)) {
					break;
				}
				if (tok.ttype != -2) {
					throw new IllegalArgumentException("Parse error");
				}
				int n1 = (int) tok.nval;
				if (tok.nextToken() != 47) {
					throw new IllegalArgumentException("Parse error");
				}
				int n2 = getint(tok);
				this.faceno.add(Integer.valueOf(n1));
				this.faceno.add(Integer.valueOf(n2));
			}
			this.faceno.add(Integer.valueOf(-1));
			this.fno += 1;
		}

		private void setgroup(int gr, int sub) {
			this.groups.add(Integer.valueOf(gr));
			this.groups.add(Integer.valueOf(sub));
			this.groups.add(Integer.valueOf(this.fno));

			if (this.grcnt.size() < gr) {
				throw new IllegalArgumentException("Parse error");
			}
			if (this.grcnt.size() == gr) {
				this.grcnt.add(Integer.valueOf(0));
			}
			this.grcnt.set(gr,
					Integer.valueOf(Math.max(this.grcnt.get(gr), sub + 1)));
		}

		private void parsegroup(StreamTokenizer tok) throws IOException {
			int n1 = getint(tok);
			int n2 = 0;

			tok.nextToken();
			if (tok.ttype == 95) {
				n2 = getint(tok);
				tok.nextToken();
			}
			setgroup(n1, n2);
			if (tok.ttype != 10)
				throw new IllegalArgumentException("Parse error");
		}

		public void readModel(InputStream fis) throws IOException {
			BufferedReader r = new BufferedReader(new InputStreamReader(fis));

			StreamTokenizer tok = new StreamTokenizer(r);
			tok.commentChar(35);
			tok.eolIsSignificant(true);
			tok.lowerCaseMode(false);
			tok.parseNumbers();
			tok.quoteChar(34);
			tok.ordinaryChar(47);

			while (tok.nextToken() != -1)
				if (tok.ttype != 10) {
					if (tok.ttype != -3) {
						throw new IllegalArgumentException("Parse error");
					}

					if (tok.sval.equals("v")) {
						Vector3 nv = new Vector3();
						nv.x = getfloat(tok);
						nv.y = getfloat(tok);
						nv.z = getfloat(tok);
						this.vertex.add(nv);
						endline(tok);
					} else if (tok.sval.equals("vt")) {
						double f1 = getfloat(tok);
						double f2 = getfloat(tok);
						this.texvert.add(new TexVertex(0, f1, f2));
						endline(tok);
					} else if (tok.sval.equals("vtc")) {
						double f1 = getfloat(tok);
						double f2 = getfloat(tok);
						TexVertex tv = new TexVertex(0, f1, f2);
						tv.r = ((float) getfloat(tok));
						tv.g = ((float) getfloat(tok));
						tv.b = ((float) getfloat(tok));
						this.texvert.add(tv);
						endline(tok);
					} else if (tok.sval.equals("f")) {
						parseface(tok);
					} else if (tok.sval.equals("g")) {
						parsegroup(tok);
					} else {
						eatline(tok);
					}
				}
			fis.close();
		}
	}
}
