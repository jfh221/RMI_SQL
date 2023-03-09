# RMI_SQL

# Práctica de Programación de Servicios y Procesos


Este es un proyecto que tiene como objetivo la construcción de un programa para llevar a cabo la gestión de los datos de los empleados de una empresa. 
Los datos que se desea conocer de cada empleado son su nombre, apellidos, edad y cargo en la empresa.
Es importante destacar que, en caso de que dos o más empleados tengan los mismos datos, la empresa les asigna un código numérico único que no puede ser modificado.

La aplicación se compone de dos programas: el cliente y el servidor. 
El servidor será el encargado de llevar a cabo las operaciones básicas de persistencia de datos, es decir, la creación, modificación, recuperación y actualización de los datos de los empleados. 
Estas operaciones serán solicitadas por el programa cliente.

Para la comunicación entre ambos programas se usará RMI (Remote Method Invocation), que permitirá que el cliente y el servidor se comuniquen a través de métodos remotos.
