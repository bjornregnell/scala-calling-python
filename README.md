# scala-calling-python

A simple way to interact with python3 from Scala in a linux environment.

* Install [Scala](https://scala-lang.org/download/)

* Download or clone this repo or just [download this scala file]()

* Run with `scala run .` and don't forget the trailing dot.

* Create a python3 virtual environment in this dir like so (example for a current Ununtu LTS fresh install):
```
  sudo apt install python3-venv      # only needed once for your system
  python3 -m venv .venv              # only needed once in each project dir
  source .venv/bin/activate          # later when done use: deactivate
  python3 -m pip install wikipedia   # only needed once for this .venv
```

* Now you can run this scala program that runs a python program string in this dir that contains the activated venv:
```
scala run . -- wiki Debugging

```

If you get this error `ModuleNotFoundError: No module named 'wikipedia'` you have forgotten to start your venv with `source .venv/bin/activate` and/or then `python3 -m pip install wikipedia`.