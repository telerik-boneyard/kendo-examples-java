package models;

import java.util.List;

public class DataSourceResult {

	private int Total;
	private List<?> Data;
	public int getTotal() {
		return Total;
	}
	public void setTotal(int total) {
		Total = total;
	}
	public List<?> getData() {
		return Data;
	}
	public void setData(List<?> data) {
		Data = data;
	}
	
}
