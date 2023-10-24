# **HOW TO GET JAVA EDITION + ANY SERVER ON YOUR LAPTOP**

## License
Copyright 2023 daytrip_.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

## Terms of Use
Basically, all of the above. If you fail to follow these instructions correctly, your computer may randomly stop working and you **WILL** get suspended when you ask for help.

# Intro
This documentation is in alpha, and is highly experimental. Think about it like this:

At Walmart, there's this thing called self-checkout, but your credit card is more likely to get leaked. Following these instructions is like self-checkout.

But you can also just have someone check out your groceries for you. In this case, just go to Jai's laptop servicing, and everything will be done for you in probably less time.

But assuming you do choose to follow these, instructions, please refer to the license and terms of use.

# Step 1
If `Eclipse Oxygen` is not already installed, download it from Software Center. ![Image]()
If it gets stuck on 0% for longer than 15 minutes, try restarting your computer and the installation. If that doesn't work, you're probably screwed, but give it a try on your home network just in case (meaning at **HOME** *[Yes someone did try to connect to their home network at school]*)

# Step 2
*LINK: https://github.com/day-trip/MinecraftInClassFTW.git*

Launch Eclipse, and when prompted, create a new workspace in the default location (AKA don't do anything and just click `continue`).
Wait for Eclipse to be done loading. On the top toolbar of Eclipse, go to `File → Import → Git → Projects from Git`. Paste in the link above and proceed. The destination folder should be a **NEW EMPTY FOLDER** under your C:\\Users\s-yourname\Documents\ folder (NOT ONEDRIVE) *(yes, some fool did put it in their downloads folder and then cleared it)*. Proceed. **WAIT FOR IT TO BE DONE BEFORE MOVING ON TO STEP 3!!!!!**

# Step 3
Go to the folder you created in Step 2 in `File Explorer`. BE SURE IT CONTAINS the following folders:
- bin
- eclipse
- jars
- src

If you cannot find it, you are probably looking in the wrong folder. Open the `eclipse/Client` subfolder. The Server folder is **NOT** required for this tutorial and can be safely ignored. Open the `.project` and `.classpath` files in NotePad or your text editor of choice. Now I bestow upon thou great pain. Remember the folder you opened that contained the four folders? Copy the absolute path, meaning it should start with `C:\\` and end with the name of the folder FOLLOWED BY a backslash (`\`, not `/`). Now you have to manually replace all instances of `X` with the path in BOTH files. And just saying, NotePad doesn't have a replace feature. Make sure to **SAVE** both files. Now in Eclipse, on the window on the left, right click the outermost folder (on top) and click `Refresh`. Click the first green play button on the top row and you should have the game running shortly.

## Some things to consider are:
- On the bottom right of the screen, there is a green bar sometimes. Be patient because the game cannot run until its completed
- The window on the bottom lets you view the logs of the game. That is confirmation that it is running.
- In the game, you need to click `ALT` in order to sign in with your Microsoft account. This is a required step to play on servers such as Hypixel

# You're all done! :)