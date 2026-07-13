package ec.edu.epn.sokoban.model.escenario;

public class Meta extends Casilla {

    public Meta(int f, int c) {
        super(f, c);
    }

    @Override
    public boolean esTransitable() {
        return true;
    }
}
