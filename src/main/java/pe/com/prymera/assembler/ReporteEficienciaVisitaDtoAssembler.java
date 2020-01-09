package pe.com.prymera.assembler;

import org.springframework.stereotype.Service;

import pe.com.prymera.dto.EficienciaVisitaAgenciaDto;
import pe.com.prymera.dto.EficienciaVisitaAnalistaDto;
import pe.com.prymera.dto.ReporteEficienciaVisitaDto;

@Service
public class ReporteEficienciaVisitaDtoAssembler {

	public ReporteEficienciaVisitaDto toDto(EficienciaVisitaAgenciaDto dto) {
		return new ReporteEficienciaVisitaDto(dto.getNombre(), dto.getPeriodoAnteriorVisitasRegistradas(),
				dto.getPeriodoAnteriorClientesNuevos(), dto.getPeriodoAnteriorDesembolsosClientesNuevos(),
				dto.getPeriodoAnteriorMontosClientesNuevos(), dto.getPeriodoActualVisitasRegistradas(),
				dto.getPeriodoActualClientesNuevos(), dto.getPeriodoActualDesembolsosClientesNuevos(),
				dto.getPeriodoActualMontosClientesNuevos());
	}
	
	public ReporteEficienciaVisitaDto toDto(EficienciaVisitaAnalistaDto dto) {
		return new ReporteEficienciaVisitaDto(dto.getNombre(), dto.getPeriodoAnteriorVisitasRegistradas(),
				dto.getPeriodoAnteriorClientesNuevos(), dto.getPeriodoAnteriorDesembolsosClientesNuevos(),
				dto.getPeriodoAnteriorMontosClientesNuevos(), dto.getPeriodoActualVisitasRegistradas(),
				dto.getPeriodoActualClientesNuevos(), dto.getPeriodoActualDesembolsosClientesNuevos(),
				dto.getPeriodoActualMontosClientesNuevos());
	}
}
