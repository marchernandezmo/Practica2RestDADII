<!DOCTYPE html>
<html lang="es>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="author" content="Marc Hern�ndez Montesinos">
<title>Rest B�sico</title>
<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
	
		//Esta funci�n a�ade un elemento a la lista de usuarios. 
		function load(id,name,surname){
			//Crea el nuevo elemento 'li' que contendr� los datos del usuario.
			var entry = document.createElement('li');
			
			//Crea un elemento que ser� el enlace para borrar el usuario creado.
			var a = document.createElement('a');
			//Se a�ade el evento que se ejecutar� al hacer clic sobre borrar.
			a.onclick = function () {
				$.ajax({
				    url: 'rest/persona/borra/' + id, //Url a ejecutar
				    type: 'DELETE', //M�todo a invocar
				    dataType: "json", //Tipo de dato enviado
				    success: function(result) {
				    	//Funci�n que se ejecuta si todo ha ido bien. En este caso, quitar el <li> que se cre� para mostrar
				    	//el usuario insertado.
				    	document.getElementById(id).remove();
				    },
			    	error: function(jqXhr, textStatus, errorMessage){
				    	alert('error');	
				    }
				});
			};
			
			
			var aEditar = document.createElement("a");
			//Texto del enlace para borrar el usuario
			var linkText = document.createTextNode(" [Borrar]");
			var linkTextEditar = document.createTextNode(" [Editar]");		
			//Se a�ade el texto a la etiqueta <a>
			a.appendChild(linkText);
			aEditar.appendChild(linkTextEditar);
			aEditar.onclick= cargaDatosUsuario.bind(this,[id]);
			
			
	
			
			//Se identifica el <li> con el id del usuario creado. As� se podr� recuperar en el futuro para eliminarlo.
			entry.id = id;		
			
			//Se a�ade texto al <li>
			entry.appendChild(document.createTextNode("("+ id + ") " +name + " " + surname));
			
			//Se pone como hijo de <li> el enlace <a> creado anteriormente  
			entry.appendChild(a);
			entry.appendChild(aEditar);
			
			//Comprobamos si existe la el objeto
			var selector = "#" + id;
			if ($(selector).length) {
				//Si ya existe, lo reemplazamos por el nuevo
				$(selector).replaceWith(entry);
			} else {
				//creamos uno nuevo
				$('#personas').append(entry);
			}
			
		}
		
		function cargaDatosUsuario(idUsuario){
			$.ajax({
			    url: 'rest/persona/get-persona/' + idUsuario, //Url a ejecutar
			    type: 'GET', //M�todo a invocar
			    dataType: "json", //Tipo de dato enviado
			    success: function(result) {
			    	$("#id").val(result.id);
			    	$("#nombre").val(result.nombre);
			    	$("#apellido1").val(result.apellido1);
			    	$("#apellido2").val(result.apellido2);
			    },
		    	error: function(jqXhr, textStatus, errorMessage){
			    	alert('error');	
			    }
			});
		}
		
		function limpiaFormulario(){
			$(":input").each(function(){
				$(this).val("");
			});
		}
		
		//Cuando el documento est� cargado en el navegador se ejecuta esta funci�n.
		$(document).ready(function(){	
			$("#limpiar").click(function(){
				console.log("Vamos a limpiar");
				limpiaFormulario();
			});
		
			//Se a�ade la funci�n que se ejecutar� al hacer clic sobre el bot�n identificado por "crearUsuario"
			$("#editarUsuario").click(function(){
				
				//Se construye el JSON a enviar {"id":"valor","name":"valor","surname":"valor"}
				//no se ponen las comillas porque la funci�n JSON.stringify ya lo hace.
				var personaInfo = {id: $('#id').val(),nombre: $('#nombre').val(),apellido1: $('#apellido1').val(), apellido2: $("#apellido2").val()};
				
			    $.ajax({
			    		data: JSON.stringify(personaInfo),
					    url: 'rest/persona/alta-usuario', //URL a la que invocar					    
					    headers: { 
				               'Accept': 'application/json',
				               'Content-Type': 'application/json' 
				           },
					    type: 'POST', //M�todo del servicio rest a ejecutar
					    dataType: "json", 
					    success: function(result) {
					    	//Esta funci�n se ejecuta si la petici�n ha ido bien. El cuerpo de la respuesta HTTP
					    	//se recibe en el par�metro 'result'
					    	//Ejemplo JSON respuesta --> {"persona":{"apellido1":"Garc�a","apellido2": "S�nchez","nombre":"Juan","id":"34"}}
					    	
					    	//Se llama a la funci�n que a�ade el elemento a la lista.
					        //Borramos el elemento y lo cargamos
					        load(result.persona.id, result.persona.nombre, result.persona.apellido1);
					    	limpiaFormulario();			    
					    },
				    	error: function(jqXhr, textStatus, errorMessage){
					    	alert('Error: ' + jqXhr.responseJSON.resultado);	
					    }
					    
					});
			    });
			
			
			$("#guardar").click(function(){
				
				//Se construye el JSON a enviar {"id":"valor","name":"valor","surname":"valor"}
				//no se ponen las comillas porque la funci�n JSON.stringify ya lo hace.
				var personaInfo = {id: $('#id').val(),nombre: $('#nombre').val(),apellido1: $('#apellido1').val(), apellido2: $("#apellido2").val()};
				//comprobamos id, si no tiene id es un alta y hacemos un POST, en caso  contrario es una modificaci�n y hacemos un PUT
				if(!$('#id').val()){
					$.ajax({
			    		data: JSON.stringify(personaInfo),
					    url: 'rest/persona/alta-usuario', //URL a la que invocar					    
					    headers: { 
				               'Accept': 'application/json',
				               'Content-Type': 'application/json' 
				           },
					    type: 'POST', //M�todo del servicio rest a ejecutar
					    dataType: "json", 
					    success: function(result) {
					    	//Esta funci�n se ejecuta si la petici�n ha ido bien. El cuerpo de la respuesta HTTP
					    	//se recibe en el par�metro 'result'
					    	//Ejemplo JSON respuesta --> {"persona":{"apellido1":"Garc�a","apellido2": "S�nchez","nombre":"Juan","id":"34"}}
					    	
					    	//Se llama a la funci�n que a�ade el elemento a la lista.
					    	load(result.persona.id, result.persona.nombre, result.persona.apellido1);
					    	limpiaFormulario();			    
					    },
				    	error: function(jqXhr, textStatus, errorMessage){
					    	alert('Error: ' + jqXhr.responseJSON.resultado);	
					    }
					    
					});	
				} else {
					$.ajax({
			    		data: JSON.stringify(personaInfo),
					    url: 'rest/persona/modifica-usuario', //URL a la que invocar					    
					    headers: { 
				               'Accept': 'application/json',
				               'Content-Type': 'application/json' 
				           },
					    type: 'PUT', //M�todo del servicio rest a ejecutar
					    dataType: "json", 
					    success: function(result) {
					    	//Esta funci�n se ejecuta si la petici�n ha ido bien. El cuerpo de la respuesta HTTP
					    	//se recibe en el par�metro 'result'
					    	//Ejemplo JSON respuesta --> {"persona":{"apellido1":"Garc�a","apellido2": "S�nchez","nombre":"Juan","id":"34"}}
					    	
					    	//Se llama a la funci�n que a�ade el elemento a la lista.
					    	load(result.persona.id, result.persona.nombre, result.persona.apellido1);
					    	limpiaFormulario();			    
					    },
				    	error: function(jqXhr, textStatus, errorMessage){
					    	alert('Error: ' + jqXhr.responseJSON.resultado);	
					    }
					    
					});
				}
			    
			    });
			
			
			//Se invoca la petici�n REST que devuelve todos los usuarios y se cargan dentro del <ul> de la p�gina.
			$.ajax({
			    url: 'rest/persona/todos',
			    type: 'GET',
			    dataType: "json",
			    success: function(result) {
			    	//Para cada elemento del array de result.personas se ejecuta la funci�n que se pasa como par�metro.
			    	//Esa funci�n tiene dos par�metros, i para la posici�n y val para el valor del elemento en curso.
			    	jQuery.each(result.personas, function(i, val) {
			    		  load(val.id, val.nombre, val.apellido1);
			    	});
			    }
			});
		});
</script>

</head>
<body>
<h1>Ejemplo de Rest</h1>
<br>
<a href="rest/persona/getDatosPersona">Llamada Get</a>
	Formulario para insertar un nuevo usuario.<br>
	Id:<input type=text id="id" disabled class="usuario-form"><br>
	Nombre:<input type=text id="nombre" class="usuario-form"><br>
	Apellido1:<input type=text id="apellido1" class="usuario-form"><br>
	Apellido2:<input type=text id="apellido2" class="usuario-form"><br>
	Dni:<input type=text id="dni" class="usuario-form"><br>
	<button id="guardar">Guardar</button>
	<button id="limpiar">Limpiar Formulario</button>

<br>
	Listado de usuarios creados
	<br>
	<ul id="personas">
	</ul>

</body>
</html>