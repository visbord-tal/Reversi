package bina.project.alphaBeta;

class Pair {

	public int i;
	public int j;

	public Pair(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public Pair(Pair p) {
		i=p.i;
		j=p.j;
	}
}