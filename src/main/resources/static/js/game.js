function letterClick(url, element,completeUrl) {
	$.ajax(url).done(function(result) {
		if(result.gameStatus == "COMPLETED"){
			window.location.href = completeUrl
		}
		$(element).prop('disabled', true);
		$('#remainingGuesses').text(result.remainingGuesses);
		$.each(result.indexesHit,function(i,item) {
			$('#gameWord span:eq(' + item + ')').text(result.letter);
		})
	}).fail(function() {
		alert("error");
	});
}
