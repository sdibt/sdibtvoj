function mathJaxFunc(){
    if($('#useMathJax').prop('checked')){
        $('head').append('<script async src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-MML-AM_CHTML"> </script>');
        $('head').append('<script type="text/x-mathjax-config">MathJax.Hub.Config({tex2jax: {inlineMath: [[\'$\',\'$\'],[\'$$$\',\'$$$\'],["\\\\(","\\\\)"]],displayMath:[[\'\\[\',\'\\]\'], [\'$$\',\'$$\'],[\'$$$$$$\',\'$$$$$$\']]}});</script>')
    } else {
        location.reload();
    }
}