package pe.com.prymera.assembler;

import org.springframework.stereotype.Service;

import pe.com.prymera.dto.AgenciaDto;
import pe.com.prymera.dto.AnalistaDto;
import pe.com.prymera.dto.ReporteEficienciaDto;

@Service
public class ReporteEficienciaDtoAssembler {

	public ReporteEficienciaDto toDto(AgenciaDto agencia) {
		return new ReporteEficienciaDto(agencia.getNombre(), agencia.getNuevoDesembolzos(), agencia.getNuevoMontos(),
				agencia.getCampaniaDesembolzos(), agencia.getCampaniaMontos(), agencia.getOtroDesembolzos(),
				agencia.getOtroMontos(), agencia.getCarteraClientes(), agencia.getCarteraCreditos(),
				agencia.getCarteraSaldos(), agencia.getCarteraAnteriorClientes(), agencia.getCarteraAnteriorCreditos(),
				agencia.getCarteraAnteriorSaldos());
	}

	public ReporteEficienciaDto toDto(AnalistaDto analista) {
		return new ReporteEficienciaDto(analista.getNombre(), analista.getNuevoDesembolzo(), analista.getNuevoMonto(),
				analista.getCampaniaDesembolzo(), analista.getCampaniaMonto(), analista.getOtroDesembolzo(),
				analista.getOtroMonto(), analista.getCarteraClientes(), analista.getCarteraCreditos(),
				analista.getCarteraSaldo(), analista.getCarteraAnteriorClientes(),
				analista.getCarteraAnteriorCreditos(), analista.getCarteraAnteriorSaldo());
	}
}
