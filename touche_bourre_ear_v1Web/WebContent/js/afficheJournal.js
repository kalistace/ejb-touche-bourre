$(document).ready(function() {
	$(".btnSmall").click(function() {
		$("#div"+$(this).attr("id")).slideToggle();
		return false;
	});
});