import os
import shutil

for file in os.listdir("./Images"):
    if file.endswith(".jpg"):
        #print(os.path.join("./Images", file))
        BW = False
        if("bw" in file):
            BW = True

        if(BW):
            #os.replace("./Images/"+file,"./BW_images/")
            shutil.move("./Images/" + file, './BW_images')
            print("moved to black white")
        else:
            #os.replace("./Images/" + file, "./RG_images/"+file)
            shutil.move("./Images/" + file, './RG_images')
            print("moved to red green")



