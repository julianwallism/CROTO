package semantic;

/**
 *
 * @author sanso
 */
public class TaulaSimbols {

    public Taula taulaNivellActual;
    public int nivell = 0;
    public TaulaSimbols() {
        this.taulaNivellActual = new Taula();
    }
    
    public void entrarBloc(){
        nivell++;
        Taula aux = new Taula(this.taulaNivellActual);
        this.taulaNivellActual = aux;
    }
    
    public void sortirBloc(){
        nivell--;
        this.taulaNivellActual = this.taulaNivellActual.taulaPare;
    }
    
    public void inserir(String id, Descripcio d){
        this.taulaNivellActual.insereix(id, d);
    }
    
    public Descripcio cercar(String id){
        Descripcio aux = this.taulaNivellActual.cerca(id);
        Taula iterador = this.taulaNivellActual;
        while(aux==null && iterador != null){
            iterador = this.taulaNivellActual.taulaPare;
            aux = iterador.cerca(id);
        }
        return aux;
    }
}
