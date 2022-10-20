# scalargrad

Scalargrad is a small library for automatic differentiation of scalar functions written in java.
This project is mainly intended for educational purposes to get an intuition for automatic differentiation.
The library is not intended for production use.
It currently only implements two operators (multiplication and power) and is not very efficient.

While this library is similar to [micrograd](https://github.com/karpathy/micrograd),
it provides a different view on gradient computation by explicitly modeling the graph
as is common in modern deep learning frameworks.
It is thus in my opinion the ideal depth of abstraction for a beginner learning about automatic differentiation,
as `Value`s in micrograd are also nodes in the graph and also the graph itself, which can be confusing to a beginner.

This clear separation of concerns aims to improve clarity and readability of the code.
Another difference between micrograd and scalargrad is that scalargrad backpropagates in a recursive manner,
while micrograd topologically sorts a list of values (which are graph nodes) to visit, which I believe subtly obscures the underlying concept.