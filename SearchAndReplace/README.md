# Search And Replace

Simple program to search and replace all instances of any String or regular expression on text based files.

`Usage: java -jar SearchAndReplace.jar [options]`
  `Options:`
    `--help, -h`
      `Usage information and help`
   ` --input, -i`
     ` Input file (mandatory)`
    `--output, -o`
     ` Generated output file name and/or path. Input file can be overwritten.`
    `--replace, -r`
     ` String or REGEX to replace in file (mandatory). Same rules as on search`
      `input apply.`
    `--search, -s`
      `String or REGEX to search for in file (mandatory). Search pattern can be`
      `a regular expression therefore some special characters like "$" need to`
      `be escaped with "\$" backspace before searching when not used in REGEX`
      `context.`
    `--verbose, -v`
      `Sets verbosity level between 0 and 3 - 0 means zero output, 3 includes`
      `loading times.`
      `Default: 1`

---

## Examples:

### Simple String Replacement:

_input.txt:_ "Lorem ipsum dolor sit amet, consetetur sadipscing elitr."

```
java -jar SearchAndReplace.jar -i input.txt -o output.txt -s Lorem -r REPLACEMENT
   ```
_output.txt:_ "REPLACEMENT ipsum dolor sit amet, consetetur sadipscing elitr." 

### Regex in Search Replacement:

_input.txt:_ "Lorem ipsum [dolor] sit amet, [consetetur] sadipscing elitr."

```
java -jar SearchAndReplace.jar -i input.txt -o output.txt -s "\[(.*?)\]" -r REPLACEMENT
   ```
_output.txt:_ "Lorem ipsum REPLACEMENT sit amet, REPLACEMENT sadipscing elitr."

### Regex Replacement:

Tip: In Windows Powershell the character $ has special meaning therefore it needs to be escaped in some cases - escaping is done by adding a grave accent "`$" infront. Using sinlge instead of double quotes also could be a solution depending on case.

_input.txt:_ "``<html><body><b>Bold Text</b></body></html>``"

```
java -jar SearchAndReplace.jar -i input.txt -o output.txt -s "<b>([^<]*)</b>" -r "$1"
   ```
_output.txt:_ "``<html><body>Bold Text</body></html>``"

### Special Character Replacement:

_input.txt:_ "``<html><body><span>Price 9.99$</span></body></html>``"

```
java -jar SearchAndReplace.jar -i input.txt -o output.txt -s "\$" -r "Dollar"
   ```
_output.txt:_ "``<html><body><span>Price 9.99Dollar</span></body></html>``"

### Replacing Whitespaces:

Due to limitations of the Unix Shell and Windows Command Line Window concerning the deletion of whitespaces infront or behind a replacement the special character backspace-"S" (\\S) can be used as an indicator for whitespaces infront, behind a replacement or as a replacmement itself. Whitespaces between characters are recognized normally.
 
In This example you want a whitespace following your replacement:

_input.txt:_ "``<html><body><span>Price 9.99$</span></body></html>``"

```
java -jar SearchAndReplace.jar -i input.txt -o output.txt -s "\$" -r "\SDollar"
   ```
_output.txt:_ "``<html><body><span>Price 9.99 Dollar</span></body></html>``"

Replacing with several whitespaces:

_input.txt:_ "Lorem ipsum replace with 10 whitespaces sadipscing elitr."

```
java -jar SearchAndReplace.jar -i input.txt -o output.txt -s "replace with 10 whitespaces" -r "\S        \S"
   ```
_output.txt:_ "Lorem ipsum&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sadipscing elitr."
