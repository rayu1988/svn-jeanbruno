package br.com.barganhas.web.beans.datamodel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.DataModel;
import javax.faces.model.DataModelEvent;
import javax.faces.model.DataModelListener;

import br.com.barganhas.business.entities.TransferObject;

@SuppressWarnings("serial")
public class CustomDataModel extends DataModel<Object> implements Serializable{
	
	private List<Object>				itens;
	private int							index = -1;
	private int 						itensPerPage;
	private int 						totalRegisters;
	private int 						currentPage;

	public <T extends TransferObject> CustomDataModel(List<T> list) {
		super();
		this.setWrappedData(list);
		this.totalRegisters = list.size();
		this.itensPerPage = 100;
	}
	
	@Override
	public int getRowCount() {
		if (this.itens == null) {
			return -1;
		}
		return this.totalRegisters;
	}

	@Override
	public Object getRowData() {
		if (this.itens == null) {
			return null;
		}
		if (!this.isRowAvailable()) {
			throw new IllegalStateException();
		}
		Object returnTemp = null;
		try{
			int position = this.currentPosition(this.index);
			if (position < this.itens.size()) {
				returnTemp = this.itens.get(this.currentPosition(this.index));
			}
		} catch (IndexOutOfBoundsException e) {
			throw new RuntimeException(e);
		}
		return returnTemp;
	}
	
	private int currentPosition(int index){
		if(index < this.itensPerPage){
			return index;
		} else {
			return index - (this.itensPerPage * this.currentPage);
		}
	}

	@Override
	public int getRowIndex() {
		return this.index;
	}

	@Override
	public Object getWrappedData() {
		return this.itens;
	}

	@Override
	public boolean isRowAvailable() {
		if (this.itens == null) {
			return false;
		}
		return this.index >= 0 && this.index < this.totalRegisters;
	}

	@Override
	public void setRowIndex(int rowIndex) {
		if (rowIndex < -1) {
			throw new IllegalArgumentException("Illegal rowIndex: " + rowIndex);
		}
		this.index = rowIndex;
		if (this.itens != null) {
			Object data = isRowAvailable() ? getRowData() : null;
			DataModelEvent event = new DataModelEvent(this, rowIndex, data);
			DataModelListener[] listeners = this.getDataModelListeners();
			for (int i = 0; i < listeners.length; i++) {
				listeners[i].rowSelected(event);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setWrappedData(Object data) {
		this.itens = (List<Object>) data;
		this.setRowIndex(this.itens != null ? 0 : -1);
	}

	public int getItensPerPage() {
		return itensPerPage;
	}

	public void setItensPerPage(int itensPerPage) {
		this.itensPerPage = itensPerPage;
	}

}
