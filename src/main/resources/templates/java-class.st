java-class(package, name, imports, annotations, accessModifier, modifier, superClass, implementedInterfaces, fields, methods) ::= <<
package $package$;

$imports:{ import | $import$};separator="\n"$

$annotations:{ annotation | $annotation$};separator="\n"$
$accessModifier$ $modifier$ class $name$ $if(superClass)$extends $superClass$$endif$ $if(implementedInterfaces)$implements $implementedInterfaces:{interface|$interface$};separator=", "$$endif$ {

    $fields:{ field | $field$};separator="\n"$

    $methods:{ method | $method$};separator="\n\n"$
}

>>
