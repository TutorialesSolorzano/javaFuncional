import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

		List<String> unicaLista = nombresVariasListas.stream()
				.flatMap(e -> e.stream().filter(f -> f.charAt(f.length() - 1) == 'o')).collect(Collectors.toList());

		unicaLista.stream().forEach(e -> System.out.println(e));

		System.out.println("-------------- PEEK ---------------");
		setUpList();
		List<User> peekList = users.stream().peek(e -> e.setName(e.getName() + " Apellido"))
				.collect(Collectors.toList());
		peekList.stream().forEach(e -> System.out.println(e.getName()));
		System.out.println("-------------- COUNT ---------------");
		setUpList();
		long count = users.stream().filter(e -> e.getId() < 3).count();
		System.out.println(count);
		System.out.println("-------------- Skip & Limit ---------------");
		String[] abcArray = { "a", "b", "c", "d", "e", "f", "g", "h" };
		List<String> abcLst = Arrays.stream(abcArray).skip(2).limit(4).collect(Collectors.toList());
		abcLst.forEach(e -> System.out.println(e));
		System.out.println("-------------- Sorted ---------------");
		setUpList();
		users = users.stream().sorted(Comparator.comparing(User::getName)).collect(Collectors.toList());

		imprimirLista();

		System.out.println("-------------- Min ---------------");
		setUpList();
		User userMin = users.stream().min(Comparator.comparing(User::getId)).orElse(null);
		System.out.println(userMin.getId());
		User userMax = users.stream().max(Comparator.comparing(User::getId)).orElse(null);
		System.out.println(userMax.getId());

		System.out.println("-------------- Distinc ---------------");
		String[] abcArrayDistinc = { "a", "b", "c", "d", "e", "f", "g", "h", "a", "c" };
		List<String> filter = Arrays.stream(abcArrayDistinc).distinct().collect(Collectors.toList());
		filter.forEach(e -> System.out.println(e));

		System.out.println("-------------- All Match, anyMatch, noneMatch ---------------");
		List<Integer> listaNumeros = Arrays.asList(100, 300, 500, 900);
		Boolean allMatch = listaNumeros.stream().allMatch(e -> e > 300);
		System.out.println(allMatch);
		Boolean anyMatch = listaNumeros.stream().anyMatch(e -> e > 300);
		System.out.println(anyMatch);
		Boolean noneMatch = listaNumeros.stream().noneMatch(e -> e > 90);
		System.out.println(noneMatch);

		System.out.println("-------------- Sum, average & range ---------------");
		setUpList();

		Double average = users.stream().mapToInt(User::getId).average().orElse(0);
		System.out.println(average);

		Integer sum = users.stream().mapToInt(User::getId).sum();
		System.out.println(sum);

		System.out.println(IntStream.range(0, 100).sum());

		System.out.println("-------------- Reduce ---------------");
		setUpList();
		Integer suma = users.stream().map(User::getId).reduce(0, Integer::sum);
		System.out.println(suma);

		System.out.println("-------------- Joining ---------------");
		setUpList();

		String names = users.stream().map(User::getName).collect(Collectors.joining(" - "));
		System.out.println(names);

		System.out.println("-------------- toSet ---------------");
		setUpList();

		Set<String> setNames = users.stream().map(User::getName).collect(Collectors.toSet());
		setNames.stream().forEach(e -> System.out.println(e));

		System.out.println("-------------- summarizingDouble ---------------");
		setUpList();

		DoubleSummaryStatistics statistics = users.stream().collect(Collectors.summarizingDouble(User::getId));
		System.out.println(statistics.getAverage());
		System.out.println(statistics.getCount());
		System.out.println(statistics.getMax());
		System.out.println(statistics.getMin());
		System.out.println(statistics.getSum());

		DoubleSummaryStatistics statistics1 = users.stream().mapToDouble(User::getId).summaryStatistics();
		System.out.println(statistics1.getAverage());
		System.out.println(statistics1.getCount());
		System.out.println(statistics1.getMax());
		System.out.println(statistics1.getMin());
		System.out.println(statistics1.getSum());

		System.out.println("-------------- partitioningBy ---------------");
		setUpList();

		List<Integer> numeros = Arrays.asList(5, 9, 11, 15);
		Map<Boolean, List<Integer>> esMayor = numeros.stream().collect(Collectors.partitioningBy(e -> e > 10));
		esMayor.get(true).stream().forEach(e -> System.out.println(e));
		esMayor.get(false).stream().forEach(e -> System.out.println(e));

		System.out.println("-------------- groupingBy ---------------");
		setUpList();

		Map<Character, List<User>> grupoAlfabetico = users.stream()
				.collect(Collectors.groupingBy(e -> new Character(e.getName().charAt(0))));
		grupoAlfabetico.get('P').stream().forEach(e -> System.out.println(e.getName()));

		System.out.println("-------------- mapping ---------------");
		setUpList();

		List<String> personas = users.stream().collect(Collectors.mapping(User::getName, Collectors.toList()));
		personas.stream().forEach(e -> System.out.println(e));

		System.out.println("-------------- paralelStream ---------------");
		setUpList();
		long time1 = System.currentTimeMillis();
		users.stream().forEach(e -> toUpperCase(e));
		long time2 = System.currentTimeMillis();
		System.out.println("Normal " + (time2 - time1));
		time1 = System.currentTimeMillis();
		users.parallelStream().forEach(e -> toUpperCase(e));
		time2 = System.currentTimeMillis();
		System.out.println("Paralel " + (time2 - time1));
	}

	private static User toUpperCase(User user) {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setName(user.getName().toUpperCase());
		return user;

	}

	private static void setUpList() {
		users = new ArrayList<>();
		users.add(new User(1, "Pieda"));
		users.add(new User(2, "Juan"));
		users.add(new User(3, "Francisco"));
		users.add(new User(4, "Roman"));
		users.add(new User(5, "Pieda"));
		users.add(new User(6, "Mauro"));
	}

	private static void imprimirLista() {
		users.stream().forEach(u -> System.out.println(u.getId() + " " + u.getName()));
	}
}
