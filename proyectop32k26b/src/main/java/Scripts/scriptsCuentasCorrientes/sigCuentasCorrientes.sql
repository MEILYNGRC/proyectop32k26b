-- Script Cuentas Corrientes Kevin Lopez 9959-24-3309
USE sig;

-- 2. Tablas Maestras (Independientes)
CREATE TABLE IF NOT EXISTS `proveedores` (
  `Procodigo` int(11) NOT NULL AUTO_INCREMENT,
  `Pronombre` varchar(100) NOT NULL,
  `Pronit` varchar(20) NOT NULL,
  `Procuentabancaria` varchar(50),
  `Proestado` char(1) NOT NULL,
  PRIMARY KEY (`Procodigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `acreedores` (
  `Acrecodigo` int(11) NOT NULL AUTO_INCREMENT,
  `Acrenombre` varchar(100) NOT NULL,
  `Acrenit` varchar(20) NOT NULL,
  `Acrecuentabancaria` varchar(50),
  `Acreestado` char(1) NOT NULL,
  PRIMARY KEY (`Acrecodigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `clientes` (
  `Cliid` int(11) NOT NULL AUTO_INCREMENT,
  `Clinombre` varchar(100) NOT NULL,
  `Clinit` varchar(20) NOT NULL,
  `Cliestado` char(1) NOT NULL,
  PRIMARY KEY (`Cliid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Tablas Transaccionales (Dependientes)
CREATE TABLE IF NOT EXISTS `cuentasporpagar` (
  `Cppcodigo` int(11) NOT NULL AUTO_INCREMENT,
  `Procodigo` int(11) DEFAULT NULL,
  `Acrecodigo` int(11) DEFAULT NULL,
  `Venid` int(11) DEFAULT NULL,
  `Cppfechaemision` date NOT NULL,
  `Cppmontototal` decimal(12,2) NOT NULL,
  `Cppsaldopendiente` decimal(12,2) NOT NULL,
  `Cppestado` char(1) NOT NULL,
  `TTid`        int(11) NOT NULL,
  `Cpporigenid` int(11) NULL,
  PRIMARY KEY (`Cppcodigo`),
  FOREIGN KEY (`Procodigo`) REFERENCES `proveedores`(`Procodigo`),
  FOREIGN KEY (`Acrecodigo`) REFERENCES `acreedores`(`Acrecodigo`),
  FOREIGN KEY (`Venid`) REFERENCES `vendedores`(`Venid`),
  FOREIGN KEY (`TTid`) REFERENCES `cattipotransaccion`(`TTid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `cuentasporcobrar` (
  `Cpccodigo` int(11) NOT NULL AUTO_INCREMENT,
  `Cliid` int(11) NOT NULL,
  `Cpcfecha` date NOT NULL,
  `Cpcmonto` decimal(12,2) NOT NULL,
  `Cpcsaldo` decimal(12,2) NOT NULL,
  `Cpcestado` char(1) NOT NULL,
  PRIMARY KEY (`Cpccodigo`),
  FOREIGN KEY (`Cliid`) REFERENCES `clientes`(`Cliid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Tabla de Cierre (Historial de Pagos)
CREATE TABLE IF NOT EXISTS `pagosemision` (
  `Pagemid` int(11) NOT NULL AUTO_INCREMENT,
  `Cppcodigo` int(11) NOT NULL,
  `Movbid` int(11) NOT NULL,
  `Pagefecha` datetime NOT NULL,
  `Pagemonto` decimal(12,2) NOT NULL,
  `Pagetipo` varchar(20),
  PRIMARY KEY (`Pagemid`),
  FOREIGN KEY (`Cppcodigo`) REFERENCES `cuentasporpagar`(`Cppcodigo`),
  FOREIGN KEY (`Movbid`) REFERENCES `movimientobancario`(`Movbid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;