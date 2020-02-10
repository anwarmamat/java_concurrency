let add1 x = x+1

let f = List.map add1

let double x = [x;x]

let g = List.map double

let sum x y = x+y

let h = List.fold_left sum 0

let prefix tl hd = hd::tl

let k = List.fold_left prefix []

let myMap f =
  let rec myMapHelper l =
    match l with
	| [] -> []
	| (h::t) -> (f h)::(myMapHelper t)
  in myMapHelper

let myFoldLeft op seed =
  let rec myFoldLeftHelper acc l =
    match l with
	| [] -> acc
	| (h::t) -> myFoldLeftHelper (op acc h) t
  in myFoldLeftHelper seed
  