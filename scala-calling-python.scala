//> using scala 3.6.4
//> using toolkit 0.7.0

val greeting = "Hello World!"

def runPython(program: String): String = 
  os.spawn(cmd = ("python3", "-c", program), stderr = os.Inherit, stdin = os.Inherit)
    .stdout.trim()

@main def Run(args: String*) = 
  if args.isEmpty then

    val out = runPython:
      s"""|
          |print("Printing Scala String using python program: $greeting ")
          |
          |""".stripMargin.trim

    println(out)
  else 
    println(s"args=$args")
    if args.startsWith(Seq("wiki")) then 

      val searchFor = args.lift(1).getOrElse("HelloWorld")
      println(s"\nDownloading from wikipedia: '$searchFor' ...\n")
      val searchResult = runPython(wikipedia(searchFor))
      println(searchResult)
    else if args.startsWith(Seq("numpy")) then 
      val xs: Vector[Float] = args.drop(1).map(_.toFloat).toVector
      println(s"Adding one to each element in $xs")
      val ys = helloNumPy(xs)
      println(ys)
  
  end if


/* How to setup python on a naked Ubuntu 24.04 and install the wikipedia python package:
    See also https://python.land/virtual-environments/virtualenv

  sudo apt install python3-venv      # only needed once for your system
  python3 -m venv .venv              # only needed once in each project dir
  source .venv/bin/activate          # later when done run this command: deactivate
  python3 -m pip install wikipedia   # only needed once for this .venv
  python3 -m pip install numpy       # only needed once for this .venv

  now you can run this python program in this dir now containing also the .venv dir:
  
  scala run -- wiki Debugging

  scala run -- numpy 1 2 3 4
*/

def wikipedia(searchString: String) = 
  s"""|import wikipedia 
      |print(wikipedia.summary("$searchString", sentences = 4))
      |""".stripMargin

def helloNumPy(xs: Vector[Float]): Vector[Float] =
  val myVectorProgram = 
    s"""|import numpy as np 
        |arr1 = np.array([${xs.mkString(", ")}])
        |arr2 = np.add(arr1, 1)
        |print(arr2)
        |""".stripMargin
  runPython(myVectorProgram).drop(1).dropRight(1).split(" ").map(_.toFloat).toVector
