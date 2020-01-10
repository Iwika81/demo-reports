package pe.com.prymera.assembler;

import org.springframework.stereotype.Service;

import pe.com.prymera.dto.EficienciaGeneralAgenciaDto;
import pe.com.prymera.dto.EficienciaGeneralAnalistaDto;
import pe.com.prymera.dto.ReporteEficienciaGeneralDto;

@Service
public class ReporteEficienciaDtoAssembler {

	public ReporteEficienciaGeneralDto toDto(EficienciaGeneralAgenciaDto agencia) {
		return new ReporteEficienciaGeneralDto(agencia.getNombre(), agencia.getNuevoDesembolzos(), agencia.getNuevoMontos(),
				agencia.getCampaniaDesembolzos(), agencia.getCampaniaMontos(), agencia.getOtroDesembolzos(),
				agencia.getOtroMontos(), agencia.getCarteraClientes(), agencia.getCarteraCreditos(),
				agencia.getCarteraSaldos(), agencia.getCarteraAnteriorClientes(), agencia.getCarteraAnteriorCreditos(),
				agencia.getCarteraAnteriorSaldos());
	}

	public ReporteEficienciaGeneralDto toDto(EficienciaGeneralAnalistaDto analista) {
		return new ReporteEficienciaGeneralDto(analista.getNombre(), analista.getNuevoDesembolzo(), analista.getNuevoMonto(),
				analista.getCampaniaDesembolzo(), analista.getCampaniaMonto(), analista.getOtroDesembolzo(),
				analista.getOtroMonto(), analista.getCarteraClientes(), analista.getCarteraCreditos(),
				analista.getCarteraSaldo(), analista.getCarteraAnteriorClientes(),
				analista.getCarteraAnteriorCreditos(), analista.getCarteraAnteriorSaldo());
	}
}
