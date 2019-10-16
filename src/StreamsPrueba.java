import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamsPrueba {

	private static List<User> users;

	public static void main(String[] args) {
		setUpList();
		users.stream().forEach(u -> u.setName(u.getName() + " Apellido"));
		imprimirLista();

		List<String> nombres = users.stream().map(u -> u.getName()).collect(Collectors.toList());
		nombres.stream().forEach(u -> System.out.println(u));
		System.out.println("-------------- FILTERS ---------------");
		setUpList();

		List<User> filterList = users.stream().filter(e -> e.getName() != "Pieda").filter(e -> e.getId() > 3)
				.collect(Collectors.toList());

		filterList.stream().forEach(e -> System.out.println(e.getName()));

		System.out.println("-------------- FIND FIRST ---------------");
		setUpList();
		User user = users.stream().filter(e -> e.getId() > 2 && e.getId() < 5).findFirst().orElse(null);

		System.out.println(user.getId() + " " + user.getName());

		System.out.println("-------------- FLAT MAP ---------------");
		List<List<String>> nombresVariasListas = new ArrayList<>(
				Arrays.asList(new ArrayList<>(Arrays.asList("Pancho", "Ramiro", "Juan")),
						new ArrayList<>(Arrays.asList("Lucio", "Mario", "Federico"))));

		List<String> unicaLista = nombresVariasListas.stream().flatMap(e -> e.stream().filter(f -> f.charAt(f.length() - 1) == 'o')).collect(Collectors.toList());

		unicaLista.stream().forEach(e -> System.out.println(e));

		System.out.println("-------------- PEEK ---------------");
		setUpList();
		List<User> peekList = users.stream().peek(e -> e.setName(e.getName() + " Apellido"))
				.collect(Collectors.toList());
		peekList.stream().forEach(e -> System.out.println(e.getName()));
		System.out.println("-------------- COUNT ---------------");
		setUpList();
		long count=users.stream().filter(e -> e.getId()<3).count();
		System.out.println(count);
		System.out.println("-------------- Skip & Limit ---------------");
		String[] abcArray= {"a","b","c","d","e","f","g","h"};
		List<String> abcLst=Arrays.stream(abcArray)
				.skip(2)
				.limit(4)
				.collect(Collectors.toList());
		abcLst.forEach(e -> System.out.println(e));
		System.out.println("-------------- Sorted ---------------");
		setUpList();
		users = users.stream()
				.sorted(Comparator.comparing(User::getName))
				.collect(Collectors.toList());
		
		imprimirLista();
		
		System.out.println("-------------- Min ---------------");
		setUpList();
		User userMin = users.stream().min(Comparator.comparing(User::getId)).orElse(null);
		System.out.println(userMin.getId());
		User userMax = users.stream().max(Comparator.comparing(User::getId)).orElse(null);
		System.out.println(userMax.getId());
		
		System.out.println("-------------- Distinc ---------------");
		String[] abcArrayDistinc= {"a","b","c","d","e","f","g","h","a","c"};
		List<String> filter= Arrays.stream(abcArrayDistinc).distinct().collect(Collectors.toList());
		filter.forEach(e -> System.out.println(e));
		
		System.out.println("-------------- All Match, anyMatch, noneMatch ---------------");
		List<Integer> listaNumeros = Arrays.asList(100, 300, 500, 900);
		Boolean allMatch= listaNumeros.stream().allMatch(e -> e>300);
		System.out.println(allMatch);
		Boolean anyMatch= listaNumeros.stream().anyMatch(e -> e>300);
		System.out.println(anyMatch);
		Boolean noneMatch= listaNumeros.stream().noneMatch(e -> e>90);
		System.out.println(noneMatch);
		
		System.out.println("-------------- Sum, average & range ---------------");
		setUpList();
		
		Double average= users.stream().mapToInt(User::getId).average().orElse(0);
		System.out.println(average);
		
		Integer sum= users.stream().mapToInt(User::getId).sum();
		System.out.println(sum);
		
		System.out.println(IntStream.range(0, 100).sum());
		
		System.out.println("-------------- Reduce ---------------");
		setUpList();
		Integer suma= users.stream().map(User::getId).reduce(0, Integer::sum);
		System.out.println(suma);
	}

	private static void setUpList() {
		users = new ArrayList<>();
		users.add(new User(1, "Pieda"));
		users.add(new User(2, "Juan"));
		users.add(new User(3, "Francisco"));
		users.add(new User(4, "Roman"));
		users.add(new User(5, "Hector"));
		users.add(new User(6, "Mauro"));
	}

	private static void imprimirLista() {
		users.stream().forEach(u -> System.out.println(u.getId() + " " + u.getName()));
	}
}
