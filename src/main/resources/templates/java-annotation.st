java-annotation(package, name, imports, annotations, accessModifier, elements) ::= <<
package $package$;

$imports:{ import | $import$};separator="\n"$

$annotations:{ annotation | $annotation$};separator="\n"$
$accessModifier$ @interface $name$ {

    $elements:{ element | $element$};separator="\n\n"$
}

>>
