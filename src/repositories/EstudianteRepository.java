package repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidades.Estudiante;

public class EstudianteRepository {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Integrador2");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		recuperarTodosEstudiantes(em);
		recuperarEstudiantePorLibreta(1, em);
		recuperarEstudiantePorGenero("masculino", em);
		recuperarEstudiantesPorCarreraPorCiudad("Tudai", "Necochea", em);
		em.getTransaction().commit();
		em.close();
		emf.close();

	}

	//2.c)
	@SuppressWarnings({"unchecked" })
	private static void recuperarTodosEstudiantes(EntityManager em) {
		List <Estudiante> estudiantes = em.createQuery("SELECT e FROM Estudiante e ORDER BY documento ASC").getResultList();
		System.out.println("");
		System.out.println("Lista de todos los estudiantes:");
		estudiantes.forEach(e-> System.out.println(e));
	}
	
	//2.d)
	private static void recuperarEstudiantePorLibreta(int numLibreta, EntityManager em) {
		Estudiante estudiante = (Estudiante) em.createQuery("SELECT e FROM Estudiante e WHERE numeroLibreta = :num").setParameter("num", numLibreta).getResultList().get(0);
		System.out.println("");
		System.out.println("Estudiante con el numero de libreta " + numLibreta + ":");
		System.out.println(estudiante);
	}
	
	//2.e)
	private static void recuperarEstudiantePorGenero(String genero, EntityManager em) {
		@SuppressWarnings("unchecked")
		List <Estudiante> estudiantes = em.createQuery("SELECT e FROM Estudiante e WHERE genero =:gen").setParameter("gen", genero).getResultList();
		System.out.println("");
		System.out.println("Lista de estudiantes con el genero " + genero + ":");
		estudiantes.forEach(e-> System.out.println(e));
	}
	
	//2.g)
	@SuppressWarnings("unused")
	private static void recuperarEstudiantesPorCarreraPorCiudad(String carrera, String ciudad, EntityManager em) {
		@SuppressWarnings("unchecked")
		List <Estudiante> estudiantes = em.createQuery("SELECT e " 
				+ "FROM Estudiante e JOIN CarreraEstudiante ce ON e.documento = ce.estudiante "
				+ "JOIN Carrera c ON ce.carrera = c.id "
				+ "WHERE c.nombre = :carr AND e.ciudadResidencia = :ciud").setParameter("carr", carrera).setParameter("ciud", ciudad).getResultList();
		System.out.println("");
		System.out.println("Estudiantes de la carrera "+carrera+" que reciden en la ciudad de "+ ciudad + ":");
		estudiantes.forEach(e-> System.out.println(e));
	}
	
}
