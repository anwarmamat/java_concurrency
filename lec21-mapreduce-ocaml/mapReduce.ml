(* 
** MapReduce in OCAML
**
** This code implements the traditional MapReduce functionality in OCAML
**
** Rance Cleaveland
** 03/18/2014
*)

(* "flatten" converts of list of lists into a single list by appending together all
 * the component lists.  This is implemented also as List.flatten; the implementation
 * is included here for pedagogical purposes.
 *)

let flatten = List.fold_left (List.append) []

(* "collect" takes a list of list of key/value pairs and converts it into a list of 
 * (key, value list) pairs.  The idea is that each key is represented uniquely in
 * the output list, with the associated list being the values in the input list
 * associated with the key.
 *
 * The implementation uses an auxiliary function, "insert", that inserts a key/value
 * pair into a list of key/value list pairs.
 *)
 


let rec insert l (k, v) =
  match l with
    [] -> [(k, [v])]
  | (k',l')::tl ->
    if (k' = k)
	then (k', v::l')::tl
	else (k', l')::(insert tl (k,v))
 

let collect mapOut = List.fold_left insert [] (flatten mapOut)






(* "mapReduce" takes two functions and a list of data and produces a list of results
 * following the MapReduce paradigm:
 *
 * . The data is assumed to be a list of key/value pairs.
 *
 * . The mapFun argument takes a single key/value pair and returns a list of
 *   list of key2/value2 value pairs
 *
 * . The reduceFun argument takes a key2 / value2 list pair and returns a list of
 *   results.
 *
 * The flow is this:  first, mapFun is applied appropriately to data to yield a list of
 * of key/value intermediate results.  collect is then applied to this list to ensure
 * that each key is associated with the list of values given it by the application of
 * mapFun.  Then, reduceFun is applied to this list to yield a list of results.
 *)
 
 
 
let mapReduce mapFun reduceFun (data: ('k * 'v) list) =
  let mapResult = List.map mapFun data in
  let collectResult = collect mapResult in
  let reduceResult = List.map reduceFun collectResult in
    flatten reduceResult
