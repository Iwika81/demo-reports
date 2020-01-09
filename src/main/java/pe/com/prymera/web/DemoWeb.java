package pe.com.prymera.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.optionconfig.tooltip.Tooltip;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pe.com.prymera.assembler.ReporteEficienciaDtoAssembler;
import pe.com.prymera.assembler.ReporteEficienciaVisitaDtoAssembler;
import pe.com.prymera.dto.AgenciaDto;
import pe.com.prymera.dto.AnalistaDto;
import pe.com.prymera.dto.EficienciaVisitaAgenciaDto;
import pe.com.prymera.dto.EficienciaVisitaAnalistaDto;
import pe.com.prymera.dto.ReporteEficienciaDto;
import pe.com.prymera.service.DataService;

@Component
//@Scope(value = "session")
@ViewScoped
@Getter
@Setter
@Log
public class DemoWeb implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private DataService service;
	@Inject
	private ReporteEficienciaDtoAssembler reporteEficienciaDtoAssembler;
	@Inject
	private ReporteEficienciaVisitaDtoAssembler reporteEficienciaVisitaDtoAssembler;

	// Reporte Gerencial
	private List<AgenciaDto> agencias;
	private TreeNode rootGeneral;
	private BarChartModel barCharGeneral;
	private BarChartModel mixedGeneral;
	private BarChartModel barCharHuacho;
	private BarChartModel mixedHuacho;
	private BarChartModel barCharBarranca;
	private BarChartModel mixedBarranca;
	private BarChartModel barCharPlazaNorte;
	private BarChartModel mixedPlazaNorte;

	// Reporte Visitas
	private List<EficienciaVisitaAgenciaDto> visitasXAgencia;
	private TreeNode rootVisita;
	private BarChartModel barCharVisitaAgencias;
	private BarChartModel mixedVisitaAgencias;
	private BarChartModel barCharVisitaHuacho;
	private BarChartModel mixedVisitaHuacho;
	private BarChartModel barCharVisitaBarranca;
	private BarChartModel mixedVisitaBarranca;
	private BarChartModel barCharVisitaPlazaNorte;
	private BarChartModel mixedVisitaPlazaNorte;

	@PostConstruct
	public void init() {
		agencias = service.crearResultas();
		visitasXAgencia = service.crearDataReporteVisita();
		generarReporteGeneral();
		generarReporteVisitas();
	}

	public void generarReporteGeneral() {
		rootGeneral = generarTree();
		barCharGeneral = generarBarCharGeneral(agencias, "Clientes Nuevos por Agencias");
		mixedGeneral = generarMixBarCharGeneral(agencias, "Cartera de clientes por Agencias");
		clearCharsGeneral();
		for (AgenciaDto agencia : agencias) {
			if (agencia.getId().equals("0001")) { // HUACHO
				barCharHuacho = generarBarCharXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Clientes Nuevos - ");
				mixedHuacho = generarMixBarCharXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Cartera de clientes - ");
			} else if (agencia.getId().equals("0004")) { // Barranca
				barCharBarranca = generarBarCharXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Clientes Nuevos - ");
				mixedBarranca = generarMixBarCharXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Cartera de clientes - ");
			} else if (agencia.getId().equals("0006")) { // Plaza Norte
				barCharPlazaNorte = generarBarCharXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Clientes Nuevos - ");
				mixedPlazaNorte = generarMixBarCharXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Cartera de clientes - ");
			}
		}
	}

	public void generarReporteVisitas() {
		rootVisita = generarTreeVisitas();
		barCharVisitaAgencias = generarBarCharVisitas(visitasXAgencia, "Visitas por Agencias");
		clearCharsVisitas();
		for (EficienciaVisitaAgenciaDto agencia : visitasXAgencia) {
			if (agencia.getId().equals("0001")) { // HUACHO
				barCharVisitaHuacho = generarBarCharVisitasXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Visitas por Agencias");
				// mixedVisitaHuacho = generarBarCharVisitasXAgencia(agencia.getAnalistas(),
				// agencia.getNombre());
			} else if (agencia.getId().equals("0004")) { // Barranca
				barCharVisitaBarranca = generarBarCharVisitasXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Visitas por Agencias");
				// mixedVisitaBarranca = generarBarCharVisitasXAgencia(agencia.getAnalistas(),
				// agencia.getNombre());
			} else if (agencia.getId().equals("0006")) { // Plaza Norte
				barCharVisitaPlazaNorte = generarBarCharVisitasXAgencia(agencia.getAnalistas(), agencia.getNombre(),
						"Visitas por Agencias");
				// mixedVisitaPlazaNorte = generarBarCharVisitasXAgencia(agencia.getAnalistas(),
				// agencia.getNombre());
			}
		}

	}

	public void filtar() {
		log.info("filtrar");
		AgenciaDto plazaNorte = new AgenciaDto("0006", "Plaza Norte", new ArrayList<>());
		AnalistaDto p1 = new AnalistaDto("Sofia Caballero", "Junior", 3, new BigDecimal(1500.0), 3,
				new BigDecimal(1500.0), 2, new BigDecimal(2000.0), 8, 3, new BigDecimal(4500.0), 6, 2,
				new BigDecimal(5500.0));
		plazaNorte.getAnalistas().add(p1);
		agencias.add(plazaNorte);
		generarReporteGeneral();
	}

	/// Metodos para generar Graficos General
	public DefaultTreeNode generarTree() {
		DefaultTreeNode root = new DefaultTreeNode(new ReporteEficienciaDto(), null);
		for (AgenciaDto agencia : agencias) {
			TreeNode var = new DefaultTreeNode(reporteEficienciaDtoAssembler.toDto(agencia), root);
			for (AnalistaDto analista : agencia.getAnalistas()) {
				new DefaultTreeNode(reporteEficienciaDtoAssembler.toDto(analista), var);
			}
		}
		return root;
	}

	public void clearCharsGeneral() {
		barCharHuacho = null;
		mixedHuacho = null;
		barCharBarranca = null;
		mixedBarranca = null;
		barCharPlazaNorte = null;
		mixedPlazaNorte = null;
	}

	public BarChartModel generarBarCharGeneral(List<AgenciaDto> agencias, String titulo) {
		BarChartModel barCharAgencias = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet dataSetClientesNuevos = new BarChartDataSet();
		dataSetClientesNuevos.setLabel("Clientes  nuevos");
		dataSetClientesNuevos.setBackgroundColor("rgb(255, 99, 132)");
		dataSetClientesNuevos.setData(agencias.stream().map(m -> m.getNuevoDesembolzos()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetClientesNuevos);

		BarChartDataSet dataSetCampanias = new BarChartDataSet();
		dataSetCampanias.setLabel("Campanias");
		dataSetCampanias.setBackgroundColor("rgb(75, 192, 192)");
		dataSetCampanias.setData(agencias.stream().map(m -> m.getCampaniaDesembolzos()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCampanias);

		BarChartDataSet dataSetOtros = new BarChartDataSet();
		dataSetOtros.setLabel("Otros");
		dataSetOtros.setBackgroundColor("rgb(75, 140, 150)");
		dataSetOtros.setData(agencias.stream().map(m -> m.getOtroDesembolzos()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetOtros);

		// LABELS
		data.setLabels(agencias.stream().map(m -> m.getNombre()).collect(Collectors.toList()));
		barCharAgencias.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		linearAxes.setStacked(true);
		cScales.addXAxesData(linearAxes);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Tooltip tooltip = new Tooltip();
		tooltip.setMode("index");
		tooltip.setIntersect(false);
		options.setTooltip(tooltip);

		Legend legend = new Legend();
		legend.setPosition("bottom");
		options.setLegend(legend);

		Title title = new Title();
		title.setDisplay(true);
		title.setText(titulo);
		options.setTitle(title);

		barCharAgencias.setOptions(options);
		// End of Options
		return barCharAgencias;
	}

	public BarChartModel generarBarCharXAgencia(List<AnalistaDto> list, String nombreAgencia, String titulo) {
		BarChartModel model = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet dataSetClientesNuevos = new BarChartDataSet();
		dataSetClientesNuevos.setLabel("Clientes  nuevos");
		dataSetClientesNuevos.setBackgroundColor("rgb(255, 99, 132)");
		dataSetClientesNuevos.setData(list.stream().map(m -> m.getNuevoDesembolzo()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetClientesNuevos);

		BarChartDataSet dataSetCampanias = new BarChartDataSet();
		dataSetCampanias.setLabel("Campanias");
		dataSetCampanias.setBackgroundColor("rgb(75, 192, 192)");
		dataSetCampanias.setData(list.stream().map(m -> m.getCampaniaDesembolzo()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCampanias);

		BarChartDataSet dataSetOtros = new BarChartDataSet();
		dataSetOtros.setLabel("Otros");
		dataSetOtros.setBackgroundColor("rgb(75, 140, 150)");
		dataSetOtros.setData(list.stream().map(m -> m.getOtroDesembolzo()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetOtros);

		// LABELS
		data.setLabels(list.stream().map(m -> m.getNombre()).collect(Collectors.toList()));
		model.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		linearAxes.setStacked(true);
		cScales.addXAxesData(linearAxes);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Tooltip tooltip = new Tooltip();
		tooltip.setMode("index");
		tooltip.setIntersect(false);
		options.setTooltip(tooltip);

		Legend legend = new Legend();
		legend.setPosition("bottom");
		options.setLegend(legend);

		Title title = new Title();
		title.setDisplay(true);
		title.setText(titulo.concat(nombreAgencia));
		options.setTitle(title);

		model.setOptions(options);
		// End of Options

		return model;
	}

	public BarChartModel generarMixBarCharGeneral(List<AgenciaDto> agencias, String titulo) {
		BarChartModel mixedAgencias = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet dataSetCarteraActual = new BarChartDataSet();
		dataSetCarteraActual.setLabel("Cartera actual");
		// dataSetCarteraActual.setBorderColor("rgb(255, 99, 132)");
		dataSetCarteraActual.setBackgroundColor("rgba(177, 99, 255)");
		dataSetCarteraActual.setData(agencias.stream().map(m -> m.getCarteraClientes()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCarteraActual);

		BarChartDataSet dataSetCarteraAnterior = new BarChartDataSet();
		dataSetCarteraAnterior.setLabel("Cartera Anterior");
		dataSetCarteraAnterior.setBorderColor("rgb(255, 99, 132)");
		dataSetCarteraAnterior.setBackgroundColor("rgba(255, 99, 132)");
		dataSetCarteraAnterior
				.setData(agencias.stream().map(m -> m.getCarteraAnteriorClientes()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCarteraAnterior);

		LineChartDataSet dataSetDiferencia = new LineChartDataSet();
		dataSetDiferencia.setData(agencias.stream().map(m -> m.getCarteraClientes() - m.getCarteraAnteriorClientes())
				.collect(Collectors.toList()));
		// dataSetDiferencia.getData().addAll(agencias.stream().map(m ->
		// m.getCarteraAnteriorClientes()).collect(Collectors.toList()));
		dataSetDiferencia.setLabel("Diferencia");
		dataSetDiferencia.setFill(false);
		dataSetDiferencia.setBorderColor("rgb(54, 162, 235)");
		// data.addChartDataSet(dataSetDiferencia);

		// LABELS
		data.setLabels(agencias.stream().map(m -> m.getNombre()).collect(Collectors.toList()));
		mixedAgencias.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearXAxes = new CartesianLinearAxes();

		CartesianLinearAxes linearYAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearYAxes.setTicks(ticks);
		// linearYAxes.setStacked(true);
		linearYAxes.setOffset(false);

		cScales.addXAxesData(linearXAxes);
		cScales.addYAxesData(linearYAxes);
		options.setScales(cScales);

		Legend legend = new Legend();
		legend.setPosition("bottom");
		options.setLegend(legend);

		Title title = new Title();
		title.setDisplay(true);
		title.setText(titulo);
		options.setTitle(title);

		mixedAgencias.setOptions(options);
		// End of Options
		return mixedAgencias;
	}

	public BarChartModel generarMixBarCharXAgencia(List<AnalistaDto> list, String nombreAgencia, String titulo) {
		BarChartModel mixModel = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet dataSetCarteraActual = new BarChartDataSet();
		dataSetCarteraActual.setLabel("Cartera actual");
		// dataSetCarteraActual.setBorderColor("rgb(255, 99, 132)");
		dataSetCarteraActual.setBackgroundColor("rgba(99, 99, 255)");
		dataSetCarteraActual.setData(list.stream().map(m -> m.getCarteraClientes()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCarteraActual);

		BarChartDataSet dataSetCarteraAnterior = new BarChartDataSet();
		dataSetCarteraAnterior.setLabel("Cartera Anterior");
		dataSetCarteraAnterior.setBorderColor("rgb(255, 99, 132)");
		dataSetCarteraAnterior.setBackgroundColor("rgba(255, 99, 132)");
		dataSetCarteraAnterior
				.setData(list.stream().map(m -> m.getCarteraAnteriorClientes()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCarteraAnterior);

		LineChartDataSet dataSetDiferencia = new LineChartDataSet();
		dataSetDiferencia.setData(list.stream().map(m -> m.getCarteraClientes() - m.getCarteraAnteriorClientes())
				.collect(Collectors.toList()));
		// dataSetDiferencia.getData().addAll(agencias.stream().map(m ->
		// m.getCarteraAnteriorClientes()).collect(Collectors.toList()));
		dataSetDiferencia.setLabel("Diferencia");
		dataSetDiferencia.setFill(false);
		dataSetDiferencia.setBorderColor("rgb(54, 162, 235)");
		// data.addChartDataSet(dataSetDiferencia);

		// LABELS
		data.setLabels(list.stream().map(m -> m.getNombre()).collect(Collectors.toList()));
		mixModel.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearXAxes = new CartesianLinearAxes();

		CartesianLinearAxes linearYAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearYAxes.setTicks(ticks);
		// linearYAxes.setStacked(true);
		linearYAxes.setOffset(false);

		cScales.addXAxesData(linearXAxes);
		cScales.addYAxesData(linearYAxes);
		options.setScales(cScales);

		Legend legend = new Legend();
		legend.setPosition("bottom");
		options.setLegend(legend);

		Title title = new Title();
		title.setDisplay(true);
		title.setText(titulo.concat(nombreAgencia));
		options.setTitle(title);

		mixModel.setOptions(options);
		// End of Options
		return mixModel;
	}

	/// Metodos para generar Graficos Visitas
	public DefaultTreeNode generarTreeVisitas() {
		DefaultTreeNode root = new DefaultTreeNode(new ReporteEficienciaDto(), null);
		for (EficienciaVisitaAgenciaDto agencia : visitasXAgencia) {
			TreeNode var = new DefaultTreeNode(reporteEficienciaVisitaDtoAssembler.toDto(agencia), root);
			for (EficienciaVisitaAnalistaDto analista : agencia.getAnalistas()) {
				new DefaultTreeNode(reporteEficienciaVisitaDtoAssembler.toDto(analista), var);
			}
		}
		return root;
	}

	public BarChartModel generarBarCharVisitas(List<EficienciaVisitaAgenciaDto> agencias, String titulo) {
		BarChartModel barCharAgencias = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet dataSetClientesNuevos = new BarChartDataSet();
		dataSetClientesNuevos.setLabel("Periodo Actual- Visitas");
		dataSetClientesNuevos.setBackgroundColor("rgb(255, 99, 132)");
		dataSetClientesNuevos.setData(
				agencias.stream().map(m -> m.getPeriodoActualVisitasRegistradas()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetClientesNuevos);

		BarChartDataSet dataSetCampanias = new BarChartDataSet();
		dataSetCampanias.setLabel("Periodo Anterior - Visitas");
		dataSetCampanias.setBackgroundColor("rgb(75, 192, 192)");
		dataSetCampanias.setData(
				agencias.stream().map(m -> m.getPeriodoAnteriorVisitasRegistradas()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCampanias);

		// LABELS
		data.setLabels(agencias.stream().map(m -> m.getNombre()).collect(Collectors.toList()));
		barCharAgencias.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		// linearAxes.setStacked(true);
		cScales.addXAxesData(linearAxes);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);

		Tooltip tooltip = new Tooltip();
		tooltip.setMode("index");
		tooltip.setIntersect(false);
		options.setTooltip(tooltip);

		Legend legend = new Legend();
		legend.setPosition("bottom");
		options.setLegend(legend);

		Title title = new Title();
		title.setDisplay(true);
		title.setText(titulo);
		options.setTitle(title);

		barCharAgencias.setOptions(options);
		// End of Options
		return barCharAgencias;
	}

	public void clearCharsVisitas() {
		barCharVisitaHuacho = null;
		mixedVisitaHuacho = null;
		barCharVisitaBarranca = null;
		mixedVisitaBarranca = null;
		barCharVisitaPlazaNorte = null;
		mixedVisitaPlazaNorte = null;
	}

	public BarChartModel generarBarCharVisitasXAgencia(List<EficienciaVisitaAnalistaDto> list, String nombreAgencia,
			String titulo) {
		BarChartModel model = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet dataSetClientesNuevos = new BarChartDataSet();
		dataSetClientesNuevos.setLabel("Periodo Actual- Visitas");
		dataSetClientesNuevos.setBackgroundColor("rgb(60, 186, 159)");
		dataSetClientesNuevos
				.setData(list.stream().map(m -> m.getPeriodoActualVisitasRegistradas()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetClientesNuevos);

		BarChartDataSet dataSetCampanias = new BarChartDataSet();
		dataSetCampanias.setLabel("Periodo Anterior - Visitas");
		dataSetCampanias.setBackgroundColor("rgb(75, 192, 192)");
		dataSetCampanias
				.setData(list.stream().map(m -> m.getPeriodoAnteriorVisitasRegistradas()).collect(Collectors.toList()));
		data.addChartDataSet(dataSetCampanias);

		// LABELS
		data.setLabels(list.stream().map(m -> m.getNombre()).collect(Collectors.toList()));
		model.setData(data);

		// Options
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		CartesianLinearAxes linearYAxes = new CartesianLinearAxes();
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		ticks.setBeginAtZero(true);
		linearYAxes.setTicks(ticks);

//		linearAxes.setStacked(true);
		cScales.addXAxesData(linearAxes);
		cScales.addYAxesData(linearYAxes);
		options.setScales(cScales);

		Tooltip tooltip = new Tooltip();
		tooltip.setMode("index");
		tooltip.setIntersect(false);
		options.setTooltip(tooltip);

		Legend legend = new Legend();
		legend.setPosition("bottom");
		options.setLegend(legend);

		Title title = new Title();
		title.setDisplay(true);
		title.setText(titulo.concat(nombreAgencia));
		options.setTitle(title);

		model.setOptions(options);
		// End of Options

		return model;
	}
}
