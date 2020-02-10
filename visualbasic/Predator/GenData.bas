Attribute VB_Name = "GenData"

Public Sub GenData(FileName As String, OutFileName As String, key As Integer)
On Error GoTo GenDataError

    Dim ts As TextStream, text As String, encText As String
    Dim fso As New FileSystemObject, inFile As File, outFile

    ' Open files
    Set inFile = fso.GetFile(FileName)
    Set ts = inFile.OpenAsTextStream(ForReading)
    Set outFile = fso.CreateTextFile(OutFileName, True)
        
    While Not ts.AtEndOfStream
        text = ts.ReadLine
        encText = Encrypt(text, key)
        outFile.Write (encText & vbCrLf)
    Wend

    outFile.Close
    ts.Close

    Exit Sub
    
GenDataError:
    MsgBox "Couldn't process: " & FileName & ", " & Err.Description & ", " & Err.HelpContext
End Sub

Public Function Encrypt(ByVal Plain As String, ByVal key As Integer)
    Dim Letter As String
    For i = 1 To Len(Plain)
        Letter = Mid$(Plain, i, 1)
        Mid$(Plain, i, 1) = Chr(Asc(Letter) + key)
    Next i
    Encrypt = Plain
End Function
