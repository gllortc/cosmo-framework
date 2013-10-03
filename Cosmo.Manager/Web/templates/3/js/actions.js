//==================================================================
// Acciones para Cosmo ORM Data Services
//------------------------------------------------------------------
// Autor:   Gerard Llort
// Versi�n: 1.0.0
//==================================================================

//------------------------------------------------------------------
// Pide confirmaci�n antes de eliminar un registro.
//------------------------------------------------------------------
// url:	URL de eliminaci�n del registro
//------------------------------------------------------------------
function deleteConfirm(url)
{
	bootbox.confirm("Est� Ud. seguro que desea eliminar el registro?", function(result) 
	{
		if (result)
		{
			window.location = url;
		}
		else
		{
			bootbox.alert("La acci�n ha sido cancelada.", function() { });
		}
	}); 
}
