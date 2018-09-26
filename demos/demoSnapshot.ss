
print $nl + "Starting " + $source;

$url = "https://www.google.com";

targetFull = "target/demos/demoSnapshot/image_full.png";
targetLogo = targetFull ~ 'full' , 'logo' ;
targetFullHtml = targetFull ~ 'png','html';
targetLogoHtml = targetLogo ~ 'png','html';

print "Capturing full page" ;
shot  file: targetFull , html: 'content' ;
print nf: targetFullHtml , '<html><body>Full page content : <hr/>' + content + '</body></html>' ;
print "Done : " + targetLogo +  " and " + targetLogoHtml ;


print "Capturing logo" ;
shot    file: targetLogo , 
        html: 'content' , 
        xpath: ".//img[@id='hplogo']";
print nf: targetLogoHtml , '<html><body>Google logo : <hr/>' + content + '</body></html>' ;
print "Done : " + targetLogo +  " and " + targetLogoHtml ;


print $nl  + "End of " + $source ;