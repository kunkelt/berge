package expert.kunkel.berge.dao;

public class Punkttyp {
	private int id;

	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

        @Override
        public String toString() {
            return getName();
        }

        @Override
        public boolean equals(Object o) {
            if (o != null && o instanceof Punkttyp) {
                Punkttyp punkttyp = (Punkttyp) o;
                return this.hashCode() == punkttyp.hashCode();
            }
            return false;
        }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
}
