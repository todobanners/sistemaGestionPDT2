# Base de datos
## Pasos para probar y ejecutar
### Paso 1
Con el sqldeveloper abierto  y con la tabla system y usuario system ejecutar el comando
``` sql
alter session set "_ORACLE_SCRIPT" = true;
```
el cual nos devuelve 
> Session alterado.

### Paso 2
Crear el usuario y base de datos necesarios para la ejecución, introducir las siguientes consultas:
```SQL
CREATE USER PROYECTO IDENTIFIED BY PROYECTO;
```
> User PROYECTO creado.

```sql
GRANT CREATE SESSION TO PROYECTO;
```
> Grant correcto.
```sql
GRANT DBA TO PROYECTO;
```
> Grant correcto.
### Paso 3
Creamos la nueva conexión a la base de datos desde la interfaz y usamos las credenciales recién creadas.
y ejecutamos el script en este repositorio.

Al ejecutarlo debera devolver algo muy parecido a:

> Table USUARIOS creado.
Table INTERVENCIONES creado.
Table TIPOS_INTERVENCIONES creado.
Table TIPOS_EQUIPOS creado.
Table EQUIPOS creado.
Table PROVEEDORES_EQUIPOS creado.
Table PAISES creado.
Table MODELOS_EQUIPOS creado.
Table MARCAS_MODELO creado.
Table PERMISOS creado.
Table PERFILES_PERMISOS creado.
Table USUARIOS_TELEFONOS creado.
Table PERFILES creado.
Table OPERACIONES creado.
Table AUDITORIAS creado.
Table INSTITUCIONES creado.
Table UBICACIONES creado.
Table BAJA_EQUIPOS creado.
Table AUDITORIAS alterado.
Table PERFILES_PERMISOS alterado.
Table USUARIOS_TELEFONOS alterado.
Table EQUIPOS alterado.
Table INTERVENCIONES alterado.
Table USUARIOS alterado.
Table UBICACIONES alterado.
Table BAJA_EQUIPOS alterado.
Table MODELOS_EQUIPOS alterado.

### Fin