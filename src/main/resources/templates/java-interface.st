java-interface(package, name, imports, annotations, accessModifier, superInterfaces, constants, methods) ::= <<
package $package$;

$imports:{ import | $import$};separator="\n"$

$annotations:{ annotation | $annotation$};separator="\n"$
$accessModifier$ interface $name$ $if(superInterfaces)$extends $superInterfaces:{interface|$interface$};separator=", "$$endif$ {

    $constants:{ constant | $constant$};separator="\n"$

    $methods:{ method | $method$};separator="\n\n"$
}

>>
