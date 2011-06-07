Introduction
============
Mandelbrot is a work-in-progress, simple fractal gallery and generator that is being developed to learn more about the different aspects involved in the integration between [Cappuccino](http://cappuccino.org)/Objective-J in the client side and [Lift](http://liftweb.net)/[Scala](http://scala-lang.org/) in the server.

Caveat: this is an early draft of a work-in-progress project.

Getting Started
===============

This project assumes you have installed a local Cappuccino distribution. It also uses the [simple-build-tool](), in short `sbt`, to build and manage the Lift project. 

Quick Install
-------------
Clone the repository in the directory of your choice:

`mkdir -p /Development/integration/`

`cd /Development/integration/`

`git clone git://github.com/ignaciocases/mandelbrot.git`

`cd mandelbrot`

Install the Cappuccino frameworks:

`capp gen -f src/main/webapp/`

Launch the simple-build-tool by typing `sbt`, and then update the project

`sbt`

`update`

Start the app container by typing

`jetty-run`

Point your browser to `http://localhost:8080/capp`.


References
==========
This application implements and uses code samples from the [Scrapbook](http://cappuccino.org/learn/tutorials/scrapbook-tutorial-1/) tutorial in the Cappuccino website and from Loverdos and Syropoulos (2010) [1] -an excellent book in Scala.



[1] Loverdos, Christos K. K. and Apostolos Syropoulos

2010 *Steps in Scala. An Introduction to Object-Functional Programming.* Cambridge: Cambridge University Press.
