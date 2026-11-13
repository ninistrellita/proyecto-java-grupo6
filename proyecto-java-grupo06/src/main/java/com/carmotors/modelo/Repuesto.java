package com.carmotors.modelo;

import java.math.BigDecimal;


public class Repuesto {

    private int id;
    private String nombre;
    private String marca;
    private int repuestoTipoId;
    private int proveedorId;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private int cantidadStock;
    private int stockMinimo;

    private transient RepuestoTipo tipo;
    private transient Proveedor proveedor;

    public Repuesto() {
    }

    public Repuesto(int id, String nombre, String marca, int repuestoTipoId, int proveedorId,
                    BigDecimal precioCompra, BigDecimal precioVenta,
                    int cantidadStock, int stockMinimo) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.repuestoTipoId = repuestoTipoId;
        this.proveedorId = proveedorId;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.cantidadStock = cantidadStock;
        this.stockMinimo = stockMinimo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getRepuestoTipoId() {
        return repuestoTipoId;
    }

    public void setRepuestoTipoId(int repuestoTipoId) {
        this.repuestoTipoId = repuestoTipoId;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public RepuestoTipo getTipo() {
        return tipo;
    }

    public void setTipo(RepuestoTipo tipo) {
        this.tipo = tipo;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }


    public boolean estaBajoStockMinimo() {
        return cantidadStock < stockMinimo;
    }

    public boolean tieneStockSuficiente(int cantidadSolicitada) {
        return cantidadStock >= cantidadSolicitada;
    }

    @Override
    public String toString() {
        return "Repuesto{id=" + id
                + ", nombre='" + nombre + "'"
                + ", marca='" + marca + "'"
                + ", stock=" + cantidadStock
                + ", stockMinimo=" + stockMinimo
                + ", precioVenta=" + precioVenta + "}";
    }
}