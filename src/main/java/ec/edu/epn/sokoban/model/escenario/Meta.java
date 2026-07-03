package ec.edu.epn.sokoban.model.escenario;

public class Meta extends Casilla {
    private boolean satisfecha;

    public Meta(int fila, int columna, boolean satisfecha) {
        super(fila, columna);
        this.satisfecha = satisfecha;
    }

    public Meta(int f, int c) {
        super(f, c);
        this.satisfecha = false;
    }

    public boolean isSatisfecha() {
        return satisfecha;
    }

    public void setSatisfecha(boolean satisfecha) {
        this.satisfecha = satisfecha;
    }

    @Override
    public boolean esTransitable() {
        return true;
    }
}
