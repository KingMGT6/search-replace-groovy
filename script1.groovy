import java.util.logging.Logger
import java.text.SimpleDateFormat

class Accn{
    static void main(args) {
        try
        {
            def dt = new Date()
            def sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
            def strt = sdf.format(dt)
            def listfilename = [] //declare list

            //enter directory path
            print "Enter a directory: "
            def path = System.console().readLine()

            //enter text to search
            print "Search text: "
            def text = System.console().readLine()
        
            //enter text to seach
            print "Replace with: "
            def reptext = System.console().readLine()

            //enter backup directory path
            print "Enter a backup file directory: "
            def bupath = System.console().readLine()

            //read directory and sub-directory files
            new File(path).eachFileRecurse { file -> 
            if(file.text.contains(text)) //used for searching text
            {
                //backup the file before modification
                //if file exist delete first then save new
                def backupfile  = new File(bupath + "\\" + file.name) //declare backup path
            
                if(backupfile.exists()) //check file if exists in backup location
                {
                    backupfile.delete() //delete existing files
                    backupfile << file.text //save
                }
                else { backupfile << file.text } //save only if not exist
            
                def wordtext = file.text.replaceAll(String.valueOf(text), reptext) //replace the searched text/word
                file.write(wordtext) //write

                listfilename.add(file.name) //add output to a list
            }
            }
            if(!listfilename.isEmpty()) //if text found and updated
            {
                //display message
                println "$text replaced by $reptext.\nDo you want to show updated file/s? (Y/N)"
                def ynoption = System.console().readLine()
                if(ynoption == "Y" || ynoption == "y")
                {
                    //display files containing the text
                    println listfilename
                }
            }
            else {print "$text not found!"} //if no text found

        
            def enDs = sdf.format(dt)
        
            Logger logger = Logger.getLogger("")
            logger.info ("Start: $strt\nDirectory: $path\nSearched text: $text\nReplaced by: $reptext\nBackup: $bupath\nEnd: $enDs") //logs

        }
        catch(Exception ex) //catch error
        {
            Logger logger = Logger.getLogger("") //error logs
            logger.info (ex.message)
        }
    }
}