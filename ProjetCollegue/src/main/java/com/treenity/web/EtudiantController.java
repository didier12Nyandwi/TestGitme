package com.treenity.web;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.treenity.dao.EtudiantRepository;
import com.treenity.entities.Etudiant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.naming.directory.DirContext;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/Etudiant")
public class EtudiantController {

	@Autowired
	private EtudiantRepository etudiantRepository;
	@Value("${dir.images}")
	private String imagedir;

	@RequestMapping(value = "/index")
	public String index(Model model, @RequestParam(name = "page", defaultValue = "0") int p,
			@RequestParam(name = "motCle", defaultValue = "") String mc) {
		Page<Etudiant> pageEtudiants = etudiantRepository.chercherEtudiants("%" + mc + "%", new PageRequest(p, 6));

		int pageCount = pageEtudiants.getTotalPages();
		int[] pages = new int[pageCount];
		for (int i = 0; i < pages.length; i++)
			pages[i] = i;
		model.addAttribute("pages", pages);
		model.addAttribute("pageEtudiants", pageEtudiants);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		return "etudiants";

	}
	
	
	@RequestMapping(value = "/indexx")
	public String indexx(Model model, @RequestParam(name = "page", defaultValue = "0")int p,
			@RequestParam(name = "motCle", defaultValue = "") String mc) {
		Page<Etudiant> pageEtudiants = etudiantRepository.chercherEtudiants("%" + mc + "%", new PageRequest(p, 10));

		int pageCount = pageEtudiants.getTotalPages();
		int[] pages = new int[pageCount];
		for (int i = 0; i < pages.length; i++)
			pages[i] = i;
		model.addAttribute("pages", pages);
		model.addAttribute("pageEtudiants", pageEtudiants);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		return "playEtudiant";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String formEtudiant(Model model) {
		model.addAttribute("etudiant", new Etudiant());
		return "formEtudiant";
	}

	@RequestMapping(value = "/SaveEtudiant", method = RequestMethod.POST)
	public String save(@Valid Etudiant et, BindingResult bindingResult,
			@RequestParam(name = "picture") MultipartFile file) throws Exception {
		if (bindingResult.hasErrors()) {
			return "formEtudiant";
		}

		if (!(file.isEmpty())) {
			et.setPhoto(file.getOriginalFilename());
		}
		etudiantRepository.save(et);

		if (!(file.isEmpty())) {
			et.setPhoto(file.getOriginalFilename());
			// transfere mon fichier dans ses dossiers
			file.transferTo(new File(imagedir + et.getId()));
		}

		return "redirect:index";

	}

	@RequestMapping(value = "/getPhoto", produces = MediaType.IMAGE_JPEG_VALUE)
	// Il va etre renvoyé dans le corps de la methode
	@ResponseBody
	public byte[] getPhoto(Long id) throws Exception {
		File f = new File(imagedir + id);
		return IOUtils.toByteArray(new FileInputStream(f));
	}

	@RequestMapping(value = "/supprimer")
	public String supprimer(Long id) {
		etudiantRepository.delete(id);
		return "redirect:index";
	}

	@RequestMapping(value = "/edit")
	public String edit(Long id, Model model) {
		Etudiant et = etudiantRepository.getOne(id);
		model.addAttribute("etudiant", et);
		return "EditEtudiant";
	}
	
	@RequestMapping(value ="/UpdateEtudiant",method=RequestMethod.POST)
	public String update(@Valid Etudiant et,BindingResult bindingResult, @RequestParam(name="picture")MultipartFile file )throws Exception{
		if(bindingResult.hasErrors()){
			return "EditEtudiant";
		}
		if(!(file.isEmpty())){
			et.setPhoto(file.getOriginalFilename());}
		etudiantRepository.saveAndFlush(et);	
		
		if(!(file.isEmpty())){
			et.setPhoto(file.getOriginalFilename());
			//transfere mon fichier dans ses dossiers
			file.transferTo(new File(imagedir+ et.getId()));
 		}	
		return "redirect:index";
		
	}
	@RequestMapping(value = "/consultEtudiant")
	public String consultEtudiant(Long id ,Model model){
		Etudiant et = etudiantRepository.getOne(id);
		model.addAttribute("etudiant", et);
		return "portfolioEtudiant";
	}

}
