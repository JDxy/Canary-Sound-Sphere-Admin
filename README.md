# Canary-Sound-Sphere-Admin
 
## Manual de usuario

### Login
Al iniciar la aplicación, el usuario deberá ingresar su nombre, contraseña y darle al botón enviar para acceder a la aplicación. (Hay que recordar que debe estar funcionando la aplicación de la API para que funcione la aplicación: https://github.com/AIPONCEP/Canary-Sound-Sphere-Api).

### Pantalla principal
Aquí se podrán ver dos pestañas para mostrar la información de la base de datos contenida en tablas con sus distintos apartados tanto de eventos como de autores. En ambos existirá cuatro opciones que se explicarán de forma resumida, pero se extenderá su funcionalidad más adelante:

* Cerrar sesión: Para salir de la sesión del usuario actual.

* Insertar: Funciona para añadir nuevos eventos o autores en la base de datos.
  
* Actualizar: El objetivo de esta función es cambiar los valores de datos ya existentes en la base de datos.

* Eliminar: Su función es eliminar datos almacenados en la base de datos.

### Pantalla Insertar
Al entrar a este apartado veremos que tenemos los siguientes elementos:

* Campos de autor o evento: Son los componentes de los autores o eventos (depende de si hemos hecho click al botón de "insertar" en la pestaña de eventos o autores).

* Boton enviar: Su función es comprobar si está bien redactada la información rellenada en los campos de autor o evento y si todo es correcto, se envían a la base de datos. En caso contrario, se muestra una alerta indicando un error.

* Boton limpiar: Elimina toda la informacion rellenada en los campos.

* Boton salir: Vuelve a la pantalla anterior.

Boton buscar: Este botón tiene como función buscar un autor o evento al hacer click en él. Para esto, se rellena un ID, se le hace click al botón y si existe, se muestra la información en el panel ubicado en la esquina inferior derecha.

### Pantalla Actualizar 

Para acceder a esta ventana, deberemos seleccionar un evento o autor ya existente en sus respectivas tablas y hacer click al botón actualizar. A continuación, veremos los siguientes elementos:

* Panel de "Datos modificados": Aquí se rellenarán los datos que queramos modificar de un evento o autor.

* Desplegable "Seleccione el campo a actualizar": La función de este elemento es seleccionar el campo de un evento o autor para poder modificarlo con la información rellenada anteriormente en el panel de "Datos modificados".
  
* Boton enviar: Su función es comprobar si hay información escrita en el panel de "Datos modificados" y que exista un campo seleccionado en el desplegable. Si todo esto se da, se actualizará la información de un evento o autor; si no, enviará al usuario una alerta informando de un error.

* Boton salir: Vuelve a la pantalla anterior.

* Panel de "Eventos Actulizado": Muestra un evento o autor con el que se a campo renovado anteriormente.

### Boton Eliminar

Para eliminar un evento o autor de la base de datos, debemos seleccionar uno de estos en una de las tablas y darle click al botón de eliminar.
  

