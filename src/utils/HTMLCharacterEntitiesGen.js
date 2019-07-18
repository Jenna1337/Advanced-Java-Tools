
/**

This code creates the from the HTMLCharacterEntities class from the data in the tables at https://en.wikipedia.org/wiki/List_of_XML_and_HTML_character_entity_references

Run this code on the above page to get the result.

**/

var pkg="utils";

HTMLCollection.prototype.map = Array.prototype.map

document.body.textContent=(
(pkg?("package "+pkg+";\n\n"):"")+
"@javax.annotation.Generated(value = {}, date = \""+new Date().toISOString()+"\")\n"+
"public final class HTMLCharacterEntities {\n"+
"\tpublic static String[][] htmlCharacterEntityReferences = {\n"+
"\t\t//@formatter:off\n"+
"\t\t//And here is the long list of html character entity references\n"+
"\t\t//See https://en.wikipedia.org/wiki/List_of_XML_and_HTML_character_entity_references \n"+
		document.getElementsByClassName("wikitable sortable")[0].tBodies.map(b=>
		b.rows.map(r=>
			[
				r.cells[0].textContent.trim().split(",").map(s=>s.trim()),
				String.fromCodePoint(Number.parseInt(r.cells[2].textContent.match(/U\+([0-9A-F]+)/i)[1], 16)).split("").map(ch=>{
						switch(ch){
							case '"': 
								return "\\\"";
							case '\\':
								return "\\\\";
							default:
								return "\\u"+ch.charCodeAt(0).toString(16).padStart(4,"0");
						}
					}).join("")]
		).map(r=>r[0].map(c=>[c,r[1]])).flat()
	).flat().map(r=>
"\t\t{\""+r[0]+"\",\""+r[1]+"\"}").join(",\n")+
"\n\t\t//@formatter:on\n"+
"\t};\n"+
"}\n");

document.body.style.whiteSpace="pre";

