package pl.edu.agh.two.abrs.model.report.schema;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ReportSchema {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "reportTable", joinColumns = @JoinColumn(name = "reportSchemaId"))
	@Column(name = "reportTable")
	private List<String> tables;

	@OneToMany(orphanRemoval = true, fetch = FetchType.EAGER)
	private List<ChartSchema> charts;

	public ReportSchema() {
	}

	public ReportSchema(String name, List<String> tables, List<ChartSchema> charts) {
		this.name = name;
		this.tables = tables;
		this.charts = charts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTables() {
		return tables;
	}

	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	public List<ChartSchema> getCharts() {
		return charts;
	}

	public void setCharts(List<ChartSchema> charts) {
		this.charts = charts;
	}
}
