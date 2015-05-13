import System.Directory
import System.IO (hFlush, stdout)
import Control.Exception (try, SomeException)

-- "Downloads" folders tend to get unwieldy.
--  This will force a decision on every downloaded file, with
--  a strong bias towards deletion.

type RemoveResult = IO (Either SomeException ())

safeRemove :: FilePath -> IO ()
safeRemove file = do
    result <- (try $ removeFile file) :: RemoveResult
    case result of
        Left _ -> removeDirectoryRecursive file
        Right _ -> return ()

p = putStrLn

decide :: FilePath -> FilePath -> IO ()
decide dir file = do
    p file
    putStr "Do you really need this? [y/N] (default N)"
    hFlush stdout
    let keep = p "kept!"
    let delete = do
        safeRemove $ dir ++ file
        p "deleted!"
    getLine >>= \input ->
        case input of
            "" -> delete
            ('y':_) -> keep
            ('Y':_) -> keep
            _ -> delete

isDownload :: FilePath -> Bool
isDownload file = and [file /= ".", file /= ".."]

removeDots :: [FilePath] -> [FilePath]
removeDots = filter isDownload

main :: IO ()
main = do
    home <- getHomeDirectory
    let directory = home ++ "/Downloads/"
    downloads <- fmap removeDots $ getDirectoryContents directory
    if length downloads == 0 then
        p "Downloads already clean. Good job!"
    else do
        let actions = fmap (decide directory) downloads
        sequence_ $ actions ++ [p "All Done!"]