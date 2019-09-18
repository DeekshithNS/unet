import org.arl.unet.*
import org.arl.unet.phy.*

subscribe phy




sendString = { dat = "helloworld", count = 1 ->
  println " Sending the string: ${dat}"
  
  count.times{ phy << new TxFrameReq(to: 2, type: DATA, data: dat)
    println "Frequency sent successfully..."

    }
}