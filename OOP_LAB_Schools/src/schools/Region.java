package schools;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Hint: to write compact stream expressions
// you can include the following
//import static java.util.stream.Collectors.*;
//import static java.util.Comparator.*;

public class Region {
	private String name;
	private Map<String, Community> communities = new HashMap<>();
	private Map<String, School> schools = new HashMap<>();
	private Map<String, Municipality> municipalities = new HashMap<>();

	public Region(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Collection<School> getSchools() {
		return schools.values();
	}

	public Collection<Community> getCommunities() {
		return communities.values();
	}

	// getMunicipalities
	public Collection<Municipality> getMunicipalies() {
		return municipalities.values();
	}

	// factory methods

	public Community newCommunity(String name, Community.Type type) {
		Community tmp = new Community(name, type);
		communities.put(name, tmp);
		return tmp;
	}

	public Municipality newMunicipality(String nome, String provincia) {
		Municipality tmp = new Municipality(nome, provincia);
		municipalities.put(nome, tmp);
		return tmp;
	}

	public Municipality newMunicipality(String nome, String provincia, Community comunita) {
		Municipality tmp = new Municipality(nome, provincia, comunita);
		municipalities.put(nome, tmp);
		return tmp;
	}

	public School newSchool(String name, String code, int grade, String description) {
		School tmp = new School(name, code, grade, description);
		schools.put(code, tmp);
		return tmp;
	}

	public Branch newBranch(int regionalCode, Municipality municipality, String address, int zipCode, School school) {
		return new Branch(regionalCode, municipality, address, zipCode, school);
	}

	public void readData(String url) throws IOException {
		// Hint: use LineUtils.loadLinesUrl(url) to load the CSV lines from a
		// URL
		List<String> lines = it.polito.utility.LineUtils.loadLinesUrl(url);
		lines.remove(0); // The first line isn't a branch
		for (String line : lines) {
			Municipality municipality = null;
			Community community = null;
			School school = null;
			String tmp = null;
			int i;

			String[] elementLine = line.split(",", 11);

			// Add the new community

			if ((tmp = elementLine[(i = 9)]).length() != 0 || (tmp = elementLine[(i = 10)]).length() != 0) {
				if ((community = communities.get(tmp)) == null)
					community = newCommunity(tmp, (i == 9) ? Community.Type.COLLINARE : Community.Type.MONTANA);
			}

			// Add the new municipality

			if ((municipality = municipalities.get(elementLine[1])) == null) {
				if (community != null)
					municipality = newMunicipality(elementLine[1], elementLine[0], community);
				else
					municipality = newMunicipality(elementLine[1], elementLine[0]);
			}

			// Add the new school

			if ((school = schools.get(elementLine[5])) == null) {
				school = newSchool(elementLine[6], elementLine[5],
						Integer.parseInt(elementLine[2].split("-")[0].trim()), elementLine[3]);
			}

			// Add the new branch

			newBranch(Integer.parseInt(elementLine[4].trim()), municipality, elementLine[7],
					Integer.parseInt(elementLine[8].trim()), school);
		}
	}

	public Map<String, Long> countSchoolsPerDescription() {
		return schools.entrySet().stream()
				.map(Map.Entry::getValue)
				.collect(Collectors.groupingBy(
						e -> e.getDescription(),
						Collectors.counting()));
	}

	public Map<String, Long> countBranchesPerMunicipality() {
		return municipalities.entrySet().stream()
				.map(Map.Entry::getValue)
				.collect(Collectors.toMap(
						e -> e.getName(), 
						e -> Long.valueOf(
								e.getBranches().size())
						));
	}

	public Map<String, Double> averageBranchesPerMunicipality() {
		return municipalities.entrySet().stream()
				.map(Map.Entry::getValue)
				.collect(Collectors.groupingBy(
						e -> e.getProvince(),
						Collectors.averagingDouble(
								e -> e.getBranches().size())
						));
	}

	public Collection<String> countSchoolsPerMunicipality() {
		return municipalities.entrySet().stream()
				.map(Map.Entry::getValue)
				.collect(Collectors.groupingBy(
						e -> e.getName(),
						Collectors.summingLong(
								e -> e.getBranches().stream()
								.map(Branch::getSchool)
								.distinct().count())
						))
				.entrySet().stream()
				.map(e -> e.getValue() + " - " + e.getKey())
				.collect(Collectors.toList());
	}

	public List<String> countSchoolsPerCommunity() {
		return communities.entrySet().stream()
				.map(Map.Entry::getValue)
				.collect(Collectors.groupingBy(
						e -> e.getName(),
						Collectors.summingLong(
								e -> e.getMunicipalities().stream()
								.flatMap(f -> f.getBranches().stream())
								.map(Branch::getSchool)
								.distinct().count())))
				.entrySet().stream()
				.sorted(Comparator.comparing(
						Map.Entry::getValue, 
						Comparator.reverseOrder())
						)
				.map(e -> e.getValue() + " - " + e.getKey())
				.collect(Collectors.toList());
	}

}
