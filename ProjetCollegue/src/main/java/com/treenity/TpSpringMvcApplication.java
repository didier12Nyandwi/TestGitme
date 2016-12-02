package com.treenity;

<<<<<<< HEAD
/**
 * Tarik changed the project
 */
=======
>>>>>>> refs/remotes/origin/master
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.treenity.dao.EtudiantRepository;
import com.treenity.entities.Etudiant;

@SpringBootApplication
public class TpSpringMvcApplication {

	public static void main(String[] args) throws ParseException {
		ApplicationContext ctx = SpringApplication.run(TpSpringMvcApplication.class, args);
		EtudiantRepository etudiantRepository = ctx.getBean(EtudiantRepository.class);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//
//		int i = 0;
//
//		while (i < 50) {
//			etudiantRepository.save(new Etudiant("Adbele Miral", df.parse("1988-11-10"), "kad@live.fr", "kad.jpg"));
//			etudiantRepository
//					.save(new Etudiant("Mamadou Jk", df.parse("1969-08-19"), "Ahmed@hotmail.fr", "ahmed.jpg"));
//
//			etudiantRepository
//					.save(new Etudiant("Sarah Kiloou", df.parse("1995-02-13"), "chuckNorris@hotmail.fr", "chuck.jpg"));
//			i++;
//
//		}
		
//		Page<Etudiant> etds = etudiantRepository.findAll(new PageRequest(0, 5));
		Page<Etudiant> etds = etudiantRepository.chercherEtudiants("%A%", new PageRequest(0, 5));
		etds.forEach(e -> System.out.println(e.getNom()));

	}
}
