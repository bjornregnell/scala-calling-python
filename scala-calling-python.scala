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

  end if


/* How to setup python on a naked Ubuntu 24.04 and install the wikipedia python package:
    See also https://python.land/virtual-environments/virtualenv

  sudo apt install python3-venv      # you only need to do this once for your system
  python3 -m venv .venv              # you only need to do this once in each project dir
  source .venv/bin/activate          # later when done use: deactivate
  python3 -m pip install wikipedia   # you only need to do this once for this .venv

  now you can run this python program in this dir now containing also the .venv dir:
  
  scala run -- wiki Debugging
*/

def wikipedia(searchString: String) = 
  s"""|import wikipedia 
      |print(wikipedia.summary("$searchString", sentences = 4))
      |""".stripMargin
