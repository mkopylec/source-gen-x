java-enum(package, name, imports, annotations, accessModifier, implementedInterfaces, values, fields, methods) ::= <<
package $package$;

$imports:{ import | $import$};separator="\n"$

$annotations:{ annotation | $annotation$};separator="\n"$
$accessModifier$ enum $name$ $if(implementedInterfaces)$implements $implementedInterfaces:{interface|$interface$};separator=", "$$endif$ {

    $values:{ value | $value$};separator=",\n"$;

    $fields:{ field | $field$};separator="\n"$

    $methods:{ method | $method$};separator="\n\n"$
}

>>
