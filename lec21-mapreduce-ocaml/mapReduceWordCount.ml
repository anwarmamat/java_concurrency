(***************************************************************************
 *
 * MapReduce Implementation of word counting.
 *
 * Author:  Rance Cleaveland
 * Date:  4/21/2014
 *
 * This file contains an implementation of a word-counting utility using an
 * OCaml implementation of MapReduce.
 *)

#load "str.cma"     (* Loads Str module, which contains regexp processing *)
#use "mapReduce.ml" (* Loads map-reduce implementation.                   *)

(**************************************************************************
 *
 * File-preparation functions.  The next group of functions are for turning
 * lists of file names into key/value pairs, where the key is the name of
 * the file and the value is a string containing the file contents.
 *)

(* "icToString" returns the contents of an input channel.  It takes a result
 * string ("partial result to date") and an input channel, reads a line
 * from the input channel, and adds the line to result as it recurses.  If
 * no line exists because of end-of-file, the string is returned.  Note that
 * "\n" is inserted because input_line strips them out.
 *)
 
let rec icToString res ic =
  try
    let line = input_line ic in
      icToString (res^"\n"^line) ic
  with
    End_of_file -> res

(* "fnameToString" takes a file name and returns a string containing the entire
 * contents of the file.
 *)
 
let fnameToString fname =
  let ic = open_in fname in
  let res = icToString "" ic in
	close_in ic;
	res

(* "fnametoWordCounterInputs" converts a list of file names into a list of 
 * file-name / file-contents pairs, suitable for inputting into the MapReduce
 * wordCounter function.
 *)
 
let fnamesToWordCounterInputs =
  List.map (fun fname -> (fname, fnameToString fname))

(**************************************************************************
 *
 * Definitions of map and reduce functions, plus auxiliary functions.
 *)
 
(* "stringToWordList" converts a string into a list of words.  The word delimiters
 * are specified in the given regular expression.
 *)
 
let stringToWordList s =
  let r = Str.regexp "[ \"\t\n\r.,:?!()`'-]+" in  (* Regexp for word boundaries *)
  Str.split r s

(* "sumList" sums a list of integers.  Note that (+) is OCaml's way of converting
 * the infix "+" operator in to the equivalent function:  (+) x y = x+y.
 *)
 
let sumList = List.fold_left (+) 0

(* "wcMapFun" is the map function for the MapReduce implementation of
 * wordCounter below.  It takes a file-name / file-contents pair and returns a
 * list of word, integer pairs; each integer is "1", representing the fact that
 * a single occurrence of the given word has been found.
 *)
 
let wcMapFun (fname, fcontents) =
  List.map (fun str -> (str, 1)) (stringToWordList fcontents)

(* "wcReduceFun" is the reduce function for the MapReduce implementation of
 * wordCounter below.  It converts a word / count-list into a word / single
 * count.
 *)
 
let wcReduceFun (word, numList) = [(word, sumList numList)]

(**************************************************************************
 *
 * MapReduce function for counting word occurrences.  "wordCounter" uses
 * mapReduce to implement word-counting, returning a list of word/count pairs.
 *)
 
let wordCounter = mapReduce wcMapFun wcReduceFun

(**************************************************************************
 * 
 * Functions for processsing output
 *)
 
let comp op = fun(w1, c1) -> fun (w2,c2) ->
	if (op c1 c2) then
		if (op c2 c1) then 0
		else 1
	else -1
 
let sortByCountDescending = List.sort (comp (<=));;

let sortByCountAscending = List.sort (comp (>=));;

(**************************************************************************
 *
 * Test data.  Following is a test of wordCounter, using files in the "textInput"
 * subdirectory.
 *)
 
let fnames = ["alice.txt";
           
				"constitution.txt";
				"declaration_of_independence.txt";
				"federalist_paper_01.txt";
				"federalist_paper_02.txt";
				"federalist_paper_03.txt";
				"federalist_paper_04.txt";
				"federalist_paper_05.txt";
				"federalist_paper_06.txt";
				"federalist_paper_07.txt";
				"federalist_paper_08.txt";
				"federalist_paper_09.txt";
				"federalist_paper_10.txt"
	   ];; 
			
let subdir = "textInput"

let fnamesQualified = List.map (fun s -> subdir^"/"^s) fnames

(**************************************************************************
 *
 * To run the test, do the following.
 *
 * 1.  Evaluate fnamesToWordCounterInputs fnamesQualified to create inputs to wordCounter.
 * 2.  Evalute wordCounter on the output of the above.
 * 3.  Call sortByCountAscending on the output to see the most commonly occurring words.
 *)
 

 let wordCounterInputs = fnamesToWordCounterInputs fnamesQualified;;
 let wordCounterOutputs = wordCounter wordCounterInputs;;
 let result = sortByCountDescending wordCounterOutputs;;

 let print_word (word,count) =
   print_string ("(" ^ word ^ "," ^ (string_of_int count) ^ ")\n")
 ;;

   List.map print_word result
 ;;
   
 

