package port.rp2.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;

import mindpin.MCMind;

public class RenderModel {
	public Vector3[] vertexList;
	public TexVertex[][] texList;
	int[][][] groups;

	public static RenderModel loadModel(String name) {
//		InputStream is = RedPowerCore.class.getResourceAsStream(name);
		InputStream is = MCMind.class.getResourceAsStream(name);

		ModelReader ml = new ModelReader();
		try {
			ml.readModel(is);
		} catch (IOException e) {
			return null;
		}
		ArrayList vtl = new ArrayList();

		int i = 0;
		while (i < ml.faceno.size()) {
			TexVertex[] tv = new TexVertex[4];
			for (int j = 0; j < 4; j++) {
				int n = ((Integer) ml.faceno.get(i)).intValue();
				i++;
				if (n < 0) {
					throw new IllegalArgumentException("Non-Quad Face");
				}

				int m = ((Integer) ml.faceno.get(i)).intValue();
				i++;

				TexVertex t = ((TexVertex) ml.texvert.get(m - 1)).copy();
				t.vtx = (n - 1);
				t.v = (1.0D - t.v);
				tv[j] = t;
			}
			int n = ((Integer) ml.faceno.get(i)).intValue();
			i++;
			if (n >= 0) {
				throw new IllegalArgumentException("Non-Quad Face");
			}

			vtl.add(tv);
		}

		RenderModel tr = new RenderModel();
		tr.vertexList = ((Vector3[]) ml.vertex.toArray(new Vector3[0]));
		tr.texList = ((TexVertex[][]) vtl.toArray(new TexVertex[0][]));

		tr.groups = new int[ml.grcnt.size()][][];
		for (i = 0; i < ml.grcnt.size(); i++) {
			int l = ((Integer) ml.grcnt.get(i)).intValue();
			tr.groups[i] = new int[l][];
			for (int j = 0; j < ((Integer) ml.grcnt.get(i)).intValue(); j++) {
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
				tr.groups[lgmn][lgsn][1] = ((Integer) ml.groups.get(i + 2))
						.intValue();
			}
			lgmn = ((Integer) ml.groups.get(i)).intValue();
			lgsn = ((Integer) ml.groups.get(i + 1)).intValue();
			lgs = ((Integer) ml.groups.get(i + 2)).intValue();
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
		public ArrayList vertex;
		public ArrayList faceno;
		public ArrayList texvert;
		public ArrayList groups;
		public ArrayList grcnt;
		int fno = 0;

		public ModelReader() {
			this.vertex = new ArrayList();
			this.texvert = new ArrayList();
			this.faceno = new ArrayList();
			this.groups = new ArrayList();
			this.grcnt = new ArrayList();
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
			this.grcnt.set(gr, Integer.valueOf(Math.max(
					((Integer) this.grcnt.get(gr)).intValue(), sub + 1)));
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
