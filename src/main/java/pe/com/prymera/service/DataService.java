package pe.com.prymera.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import pe.com.prymera.dto.EficienciaGeneralAgenciaDto;
import pe.com.prymera.dto.EficienciaGeneralAnalistaDto;
import pe.com.prymera.dto.EficienciaVisitaAgenciaDto;
import pe.com.prymera.dto.EficienciaVisitaAnalistaDto;

@Service
public class DataService {

	public List<EficienciaGeneralAgenciaDto> crearResultas() {

		List<EficienciaGeneralAgenciaDto> agencias = new ArrayList<EficienciaGeneralAgenciaDto>();

		EficienciaGeneralAgenciaDto comas = new EficienciaGeneralAgenciaDto("0001", "Comas", new ArrayList<>());
		EficienciaGeneralAgenciaDto barranca = new EficienciaGeneralAgenciaDto("0004", "Barranca", new ArrayList<>());

		EficienciaGeneralAnalistaDto a1 = new EficienciaGeneralAnalistaDto("Juan Perez", "Senior", 1, new BigDecimal(1500.0), 4, new BigDecimal(1500.0),
				2, new BigDecimal(1000.0), 4, 3, new BigDecimal(4500.0), 8, 2, new BigDecimal(5500.0));
		EficienciaGeneralAnalistaDto a2 = new EficienciaGeneralAnalistaDto("Martin Perez", "Senior", 2, new BigDecimal(1500.0), 4, new BigDecimal(1500.0),
				2, new BigDecimal(1000.0), 3, 3, new BigDecimal(4500.0), 7, 2, new BigDecimal(5500.0));
		EficienciaGeneralAnalistaDto a3 = new EficienciaGeneralAnalistaDto("Dany Perez", "Senior", 5, new BigDecimal(1500.0), 4, new BigDecimal(1500.0),
				2, new BigDecimal(1000.0), 8, 3, new BigDecimal(4500.0), 6, 2, new BigDecimal(5500.0));
		comas.getAnalistas().add(a1);
		comas.getAnalistas().add(a2);
		comas.getAnalistas().add(a3);

		EficienciaGeneralAnalistaDto b1 = new EficienciaGeneralAnalistaDto("Martin Ramirez", "Senior", 3, new BigDecimal(4500.0), 3,
				new BigDecimal(6000.0), 2, new BigDecimal(2000.0), 8, 3, new BigDecimal(4500.0), 6, 2,
				new BigDecimal(5500.0));
		EficienciaGeneralAnalistaDto b2 = new EficienciaGeneralAnalistaDto("Jorgue Ramirez", "Senior", 0, BigDecimal.ZERO, 3, new BigDecimal(6000.0), 2,
				new BigDecimal(2000.0), 8, 3, new BigDecimal(4500.0), 6, 2, new BigDecimal(5500.0));
		barranca.getAnalistas().add(b1);
		barranca.getAnalistas().add(b2);

		agencias.add(comas);
		agencias.add(barranca);

		return agencias;

	}

	public List<EficienciaVisitaAgenciaDto> crearDataReporteVisita() {
		List<EficienciaVisitaAgenciaDto> visitas = new ArrayList<>();
		EficienciaVisitaAgenciaDto comas = new EficienciaVisitaAgenciaDto("0001", "Comas", new ArrayList<>());
		EficienciaVisitaAgenciaDto barranca = new EficienciaVisitaAgenciaDto("0004", "Barranca", new ArrayList<>());
		EficienciaVisitaAgenciaDto plazaNorte = new EficienciaVisitaAgenciaDto("0006", "Plaza Norte",
				new ArrayList<>());

		EficienciaVisitaAnalistaDto a1 = new EficienciaVisitaAnalistaDto("Juan Perez", 0, 4, 2, new BigDecimal(2000.0),
				4, 2, 1, new BigDecimal(1000.0));
		EficienciaVisitaAnalistaDto a2 = new EficienciaVisitaAnalistaDto("Martin Perez", 0, 4, 2,
				new BigDecimal(2000.0), 4, 2, 1, new BigDecimal(1000.0));
		EficienciaVisitaAnalistaDto a3 = new EficienciaVisitaAnalistaDto("Dany Perez", 0, 4, 2, new BigDecimal(2000.0),
				4, 2, 1, new BigDecimal(1000.0));
		comas.getAnalistas().add(a1);
		comas.getAnalistas().add(a2);
		comas.getAnalistas().add(a3);
		
		EficienciaVisitaAnalistaDto b1 = new EficienciaVisitaAnalistaDto("Martin Ramirez", 2, 4, 2, new BigDecimal(2000.0),
				4, 2, 1, new BigDecimal(1000.0));
		EficienciaVisitaAnalistaDto b2 = new EficienciaVisitaAnalistaDto("Jorgue Ramirez", 1, 2, 2, new BigDecimal(2000.0),
				4, 2, 1, new BigDecimal(1000.0));
		barranca.getAnalistas().add(b1);
		barranca.getAnalistas().add(b2);

		EficienciaVisitaAnalistaDto c1 = new EficienciaVisitaAnalistaDto("Sofia Caballero", 2, 4, 2, new BigDecimal(2000.0),
				4, 2, 1, new BigDecimal(1000.0));
		plazaNorte.getAnalistas().add(c1);
		
		visitas.add(comas);
		visitas.add(barranca);
		visitas.add(plazaNorte);

		return visitas;
	}
}
