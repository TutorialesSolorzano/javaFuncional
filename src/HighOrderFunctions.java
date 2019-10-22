import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HighOrderFunctions implements SumarInterfaz{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HighOrderFunctions hof= new HighOrderFunctions();
		
		SumarInterfaz sumar= (a,b) -> a+b;
		
		System.out.println(hof.sumarHighOrderFunction(sumar, 6, 1));
		
		Function<String, String> funcion= e-> e.toUpperCase();
		hof.imprimeMayuscula(funcion, "animo");
		
		List<Integer> numeros= Arrays.asList(1,2,6,-1,-4);
		BiFunction<
		List<Integer>,
		Predicate<Integer>,
		List<Integer>
		> filtrar;
		filtrar= (lista, predicado)->lista.stream().filter(e -> predicado.test(e)).collect(Collectors.toList());
	System.out.println(filtrar.apply(numeros, e-> e>0));	
	
	List<String> nombres= new ArrayList<>();
	nombres.add("Hugo");
	nombres.add("Francisco");
	nombres.add("Luca");
	
	hof.filtrar(nombres, e -> System.out.println(e), 6);
	}

	@Override
	public int apply(int a, int b) {
		// TODO Auto-generated method stub
		return a+b;
	}
	
	public int sumarHighOrderFunction(SumarInterfaz sumar, int a, int b) {
		return sumar.apply(a, b);
	}

	public void imprimeMayuscula(Function<String, String> funcion, String palabra) {
		System.out.println(funcion.apply(palabra));
	}
	
	public void filtrar(List<String> lista, Consumer<String> consumer, int maximoCaracteres) {
		lista.stream().filter(logicaPredicado(maximoCaracteres)).forEach(consumer);
	}
	
	public Predicate<String> logicaPredicado(int maximoCaracteres){
		return e -> e.length()<maximoCaracteres;
	}
	
	
}
