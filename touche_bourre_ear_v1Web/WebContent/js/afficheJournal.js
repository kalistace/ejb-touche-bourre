$(document).ready(function() {
	$(".btnSmall").click(function() {
		$("#div"+$(this).attr("id")).fadeToggle();
		return false;
	});
});