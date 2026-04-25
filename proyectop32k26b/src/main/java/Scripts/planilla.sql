/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  andre
 * Created: 24/04/2026
 */

DROP DATABASE IF EXISTS planilla;
CREATE DATABASE planilla;
USE planilla;

CREATE TABLE puestos (
    id_puesto INT AUTO_INCREMENT PRIMARY KEY,
    nombre_puesto VARCHAR(50) NOT NULL,
    salario_base DECIMAL(10,2) NOT NULL
);

CREATE TABLE empleados (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    dpi VARCHAR(20),
    id_puesto INT,
    salario_base DECIMAL(10,2) NOT NULL,
    fecha_ingreso DATE,
    FOREIGN KEY (id_puesto) REFERENCES puestos(id_puesto)
);

CREATE TABLE deducciones (
    id_deduccion INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    tipo VARCHAR(20),
    porcentaje DECIMAL(5,2) NOT NULL
);

CREATE TABLE bonificaciones (
    id_bono INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    monto DECIMAL(10,2) NOT NULL
);

CREATE TABLE planilla (
    id_planilla INT AUTO_INCREMENT PRIMARY KEY,
    id_empleado INT,
    salario_base DECIMAL(10,2),
    total_bonificaciones DECIMAL(10,2),
    total_deducciones DECIMAL(10,2),
    salario_liquido DECIMAL(10,2),
    fecha_pago DATE,
    FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado)
);

CREATE TABLE detalle_deducciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_planilla INT,
    id_deduccion INT,
    monto DECIMAL(10,2),
    FOREIGN KEY (id_planilla) REFERENCES planilla(id_planilla),
    FOREIGN KEY (id_deduccion) REFERENCES deducciones(id_deduccion)
);

CREATE TABLE detalle_bonificaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_planilla INT,
    id_bono INT,
    monto DECIMAL(10,2),
    FOREIGN KEY (id_planilla) REFERENCES planilla(id_planilla),
    FOREIGN KEY (id_bono) REFERENCES bonificaciones(id_bono)
);

